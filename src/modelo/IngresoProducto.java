package modelo;

import java.io.BufferedReader;
import java.io.File;
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
import modelo.Producto;

public class IngresoProducto extends Movimiento {
   private String tipoMovimiento = "Ingreso";
   private String proveedorProducto;
   
    public IngresoProducto(int id, String producto, int cantidad, Date fecha, String proveedorProducto) {
        super(id, producto, cantidad, fecha);
        this.proveedorProducto = proveedorProducto;
    }
   
    public static void ingresarProducto(Scanner scanner) {
        List<Producto> productos = Producto.cargarProductosDesdeArchivo();
        List<Proveedor> proveedores = Proveedor.cargarProveedoresDesdeArchivo();

        System.out.println("Seleccione el producto:");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            System.out.println((i + 1) + ". " + producto.getNombre());
        }
        int opcionProducto = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer la opción

        System.out.println("Seleccione el proveedor:");
        for (int i = 0; i < proveedores.size(); i++) {
            Proveedor proveedor = proveedores.get(i);
            System.out.println((i + 1) + ". " + proveedor.getRazonSocial());
        }
        int opcionProveedor = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer la opción

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer la cantidad

        Date fecha = new Date(); // Fecha actual

        int id = generarIdUnicoIngreso();
        String productoSeleccionado = productos.get(opcionProducto - 1).getNombre();
        String proveedorSeleccionado = proveedores.get(opcionProveedor - 1).getRazonSocial();

        IngresoProducto ingreso = new IngresoProducto(id, productoSeleccionado, cantidad, fecha, proveedorSeleccionado);
        guardarNuevoIngresoEnArchivo(ingreso);

        System.out.println("Ingreso de producto registrado correctamente.");
        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }

    public static void editarIngreso(Scanner scanner) {
        List<IngresoProducto> ingresos = cargarIngresosDesdeArchivo();

        System.out.print("Ingrese el ID del ingreso a editar: ");
        int idIngreso = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer el ID

        IngresoProducto ingresoEncontrado = null;
        for (IngresoProducto ingreso : ingresos) {
            if (ingreso.getId() == idIngreso) {
                ingresoEncontrado = ingreso;
                break;
            }
        }

        if (ingresoEncontrado != null) {
            System.out.println("Datos actuales del ingreso:");
            System.out.println("Producto: " + ingresoEncontrado.getProducto());
            System.out.println("Cantidad: " + ingresoEncontrado.getCantidad());
            System.out.println("Fecha: " + ingresoEncontrado.getFecha());
            System.out.println("Proveedor: " + ingresoEncontrado.getProveedorProducto());

            System.out.print("Ingrese el nuevo producto (dejar en blanco para mantener): ");
            String nuevoProducto = scanner.nextLine();
            if (!nuevoProducto.isEmpty()) {
                ingresoEncontrado.setProducto(nuevoProducto);
            }

            System.out.print("Ingrese la nueva cantidad (dejar en blanco para mantener): ");
            String nuevaCantidadStr = scanner.nextLine();
            if (!nuevaCantidadStr.isEmpty()) {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                ingresoEncontrado.setCantidad(nuevaCantidad);
            }

            System.out.print("Ingrese el nuevo proveedor (dejar en blanco para mantener): ");
            String nuevoProveedor = scanner.nextLine();
            if (!nuevoProveedor.isEmpty()) {
                ingresoEncontrado.setProveedorProducto(nuevoProveedor);
            }

            sobrescribirArchivoConIngresos(ingresos);
            System.out.println("Ingreso editado correctamente.");
        } else {
            System.out.println("No se encontró un ingreso con el ID especificado.");
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }
    
    private static int generarIdUnicoIngreso() {
        List<IngresoProducto> ingresos = cargarIngresosDesdeArchivo();
        int maxId = 0;

        for (IngresoProducto ingreso : ingresos) {
            if (ingreso.getId() > maxId) {
                maxId = ingreso.getId();
            }
        }

        return maxId + 1;
    }

    
    @Override
    public void listarMovimientos() {
        List<IngresoProducto> ingresos = cargarIngresosDesdeArchivo();

        if (ingresos.isEmpty()) {
            System.out.println("No hay ingresos registrados.");
        } else {
            System.out.println("Lista de ingresos:");
            for (IngresoProducto ingreso : ingresos) {
                System.out.println("ID: " + ingreso.getId());
                System.out.println("Producto: " + ingreso.getProducto());
                System.out.println("Cantidad: " + ingreso.getCantidad());
                System.out.println("Fecha: " + ingreso.getFecha());
                System.out.println("Proveedor: " + ingreso.getProveedorProducto());
                System.out.println("------------------------");
            }
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    private static void guardarNuevoIngresoEnArchivo(IngresoProducto ingreso) {
        
        // Carpeta para guardar el archivo
        String carpeta = "data/";
        String nombreArchivo = "ingresos.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificamos si existe la carpeta
        File directorio = new File(carpeta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        // Operación de escritura        
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = dateFormat.format(ingreso.getFecha());
            writer.println(ingreso.getId() + "," + ingreso.getProducto() + "," + ingreso.getCantidad() + ","
                    + fechaFormateada + "," + ingreso.getProveedorProducto());
        } catch (IOException e) {
            System.out.println("Error al guardar el ingreso en el archivo.");
            e.printStackTrace();
        }
    }
    
    public static List<IngresoProducto> cargarIngresosDesdeArchivo() {
        List<IngresoProducto> ingresos = new ArrayList<>();
        
        // Ruta del archivo
        String carpeta = "data/";
        String nombreArchivo = "ingresos.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificar si existe el archivo
        File archivo = new File(rutaCompleta);
        if (!archivo.exists()) {
            System.out.println("El archivo de ingresos no existe.");
            return ingresos; // Retorna una lista vacía si el archivo no existe
        }
        
        //Operación de lectura
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0]);
                String producto = datos[1];
                int cantidad = Integer.parseInt(datos[2]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = dateFormat.parse(datos[3]);
                String proveedor = datos[4];
                IngresoProducto ingreso = new IngresoProducto(id, producto, cantidad, fecha, proveedor);
                ingresos.add(ingreso);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar los ingresos desde el archivo.");
            e.printStackTrace();
        }

        return ingresos;
    }

    private static void sobrescribirArchivoConIngresos(List<IngresoProducto> ingresos) {
        // Carpeta para guardar el archivo
        String carpeta = "data/";
        String nombreArchivo = "ingresos.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificamos si existe la carpeta
        File directorio = new File(carpeta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        // Sobreescritura
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (IngresoProducto ingreso : ingresos) {
                String fechaFormateada = dateFormat.format(ingreso.getFecha());
                writer.println(ingreso.getId() + "," + ingreso.getProducto() + "," + ingreso.getCantidad() + ","
                    + fechaFormateada + "," + ingreso.getProveedorProducto());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los ingresos en el archivo.");
            e.printStackTrace();
        }
    }

    public String getProveedorProducto() {
        return proveedorProducto;
    }
    public void setProveedorProducto(String proveedorProducto) {
        this.proveedorProducto = proveedorProducto;
    }
}

  