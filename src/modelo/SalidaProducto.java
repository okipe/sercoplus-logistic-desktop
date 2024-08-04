package modelo;

import modelo.Movimiento;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SalidaProducto extends Movimiento {
    private String tipoMovimiento = "Salida";
    private String sedeDestino;

    public SalidaProducto(int id, String producto, int cantidad, Date fecha, String sedeDestino) {
        super(id, producto, cantidad, fecha);
        this.sedeDestino = sedeDestino;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(String sedeDestino) {
        this.sedeDestino = sedeDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
    
    public static void retirarProducto(Scanner scanner) {
        List<Producto> productos = Producto.cargarProductosDesdeArchivo();

        System.out.println("Seleccione el producto:");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            System.out.println((i + 1) + ". " + producto.getNombre());
        }
        int opcionProducto = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer la opción

        System.out.print("Ingrese la sede de destino: ");
        String sedeDestino = scanner.nextLine();

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer la cantidad

        Date fecha = new Date(); // Fecha actual

        int id = generarIdUnicoSalida();
        String productoSeleccionado = productos.get(opcionProducto - 1).getNombre();

        SalidaProducto salida = new SalidaProducto(id, productoSeleccionado, cantidad, fecha, sedeDestino);
        guardarNuevaSalidaEnArchivo(salida);

        System.out.println("Salida de producto registrada correctamente.");
        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }
	
	// Ver si funciona
    
    public static void editarRetiro(Scanner scanner) {
        List<SalidaProducto> salidas = cargarSalidasDesdeArchivo();

        System.out.print("Ingrese el ID de la salida a editar: ");
        int idSalida = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer el ID

        SalidaProducto salidaEncontrada = null;
        for (SalidaProducto salida : salidas) {
            if (salida.getId() == idSalida) {
                salidaEncontrada = salida;
                break;
            }
        }

        if (salidaEncontrada != null) {
            System.out.println("Datos actuales de la salida:");
            System.out.println("Producto: " + salidaEncontrada.getProducto());
            System.out.println("Cantidad: " + salidaEncontrada.getCantidad());
            System.out.println("Fecha: " + salidaEncontrada.getFecha());
            System.out.println("Sede de destino: " + salidaEncontrada.getSedeDestino());

            System.out.print("Ingrese el nuevo producto (dejar en blanco para mantener): ");
            String nuevoProducto = scanner.nextLine();
            if (!nuevoProducto.isEmpty()) {
                salidaEncontrada.setProducto(nuevoProducto);
            }

            System.out.print("Ingrese la nueva cantidad (dejar en blanco para mantener): ");
            String nuevaCantidadStr = scanner.nextLine();
            if (!nuevaCantidadStr.isEmpty()) {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                salidaEncontrada.setCantidad(nuevaCantidad);
            }

            System.out.print("Ingrese la nueva sede de destino (dejar en blanco para mantener): ");
            String nuevaSedeDestino = scanner.nextLine();
            if (!nuevaSedeDestino.isEmpty()) {
                salidaEncontrada.setSedeDestino(nuevaSedeDestino);
            }

            sobrescribirArchivoConSalidas(salidas);
            System.out.println("Salida editada correctamente.");
        } else {
            System.out.println("No se encontró una salida con el ID especificado.");
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }
    
    @Override
    public void listarMovimientos() {
        List<SalidaProducto> salidas = cargarSalidasDesdeArchivo();

        if (salidas.isEmpty()) {
            System.out.println("No hay salidas registradas.");
        } else {
            System.out.println("Lista de salidas:");
            for (SalidaProducto salida : salidas) {
                System.out.println("ID: " + salida.getId());
                System.out.println("Producto: " + salida.getProducto());
                System.out.println("Cantidad: " + salida.getCantidad());
                System.out.println("Fecha: " + salida.getFecha());
                System.out.println("Sede de destino: " + salida.getSedeDestino());
                System.out.println("------------------------");
            }
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    private static int generarIdUnicoSalida() {
        List<SalidaProducto> salidas = cargarSalidasDesdeArchivo();
        int maxId = 0;

        for (SalidaProducto salida : salidas) {
            if (salida.getId() > maxId) {
                maxId = salida.getId();
            }
        }

        return maxId + 1;
    }
    
    private static void guardarNuevaSalidaEnArchivo(SalidaProducto salida) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("salidas.txt", true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = dateFormat.format(salida.getFecha());
            writer.println(salida.getId() + "," + salida.getProducto() + "," + salida.getCantidad() + ","
                    + fechaFormateada + "," + salida.getSedeDestino());
        } catch (IOException e) {
            System.out.println("Error al guardar la salida en el archivo.");
            e.printStackTrace();
        }
    }

    public static List<SalidaProducto> cargarSalidasDesdeArchivo() {
        List<SalidaProducto> salidas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("salidas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0]);
                String producto = datos[1];
                int cantidad = Integer.parseInt(datos[2]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = dateFormat.parse(datos[3]);
                String sedeDestino = datos[4];

                SalidaProducto salida = new SalidaProducto(id, producto, cantidad, fecha, sedeDestino);
                salidas.add(salida);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar las salidas desde el archivo.");
            e.printStackTrace();
        }

        return salidas;
    }
    
    private static void sobrescribirArchivoConSalidas(List<SalidaProducto> salidas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("salidas.txt"))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (SalidaProducto salida : salidas) {
                String fechaFormateada = dateFormat.format(salida.getFecha());
                writer.println(salida.getId() + "," + salida.getProducto() + "," + salida.getCantidad() + ","
                        + fechaFormateada + "," + salida.getSedeDestino());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las salidas en el archivo.");
            e.printStackTrace();
        }
    }
}
