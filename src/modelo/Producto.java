// Producto.java

package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.IngresoProducto;
import modelo.SalidaProducto;

public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String marca;
    private float precio;

    public Producto(int id, String codigo, String nombre, String marca, float precio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    
    public static void agregarTipoProducto(Scanner scanner) {
        System.out.print("Ingrese el código del producto: ");
        String codigo = scanner.nextLine();

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la marca del producto: ");
        String proveedor = scanner.nextLine();

        System.out.print("Ingrese el precio del producto: ");
        float precio = scanner.nextFloat();
        scanner.nextLine(); 

        int id = generarIdUnicoProducto();

        Producto producto = new Producto(id, codigo, nombre, proveedor, precio);
        guardarNuevoProductoEnArchivo(producto);

        System.out.println("Producto agregado correctamente.");
        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }

    
    public static void editarTipoProducto(Scanner scanner) {
        List<Producto> productos = cargarProductosDesdeArchivo();

        System.out.print("Ingrese el ID del producto a editar: ");
        int idProducto = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer el ID

        Producto productoEncontrado = null;
        for (Producto producto : productos) {
            if (producto.getId() == idProducto) {
                productoEncontrado = producto;
                break;
            }
        }

        if (productoEncontrado != null) {
            System.out.println("Datos actuales del producto:");
            System.out.println("Código: " + productoEncontrado.getCodigo());
            System.out.println("Nombre: " + productoEncontrado.getNombre());
            System.out.println("Marca: " + productoEncontrado.getMarca());
            System.out.println("Precio: " + productoEncontrado.getPrecio());
           
            System.out.print("Ingrese el nuevo código del producto (dejar en blanco para mantener): ");
            String nuevoCodigo = scanner.nextLine();
            if (!nuevoCodigo.isEmpty()) {
                productoEncontrado.setCodigo(nuevoCodigo);
            }

            System.out.print("Ingrese el nuevo nombre del producto (dejar en blanco para mantener): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isEmpty()) {
                productoEncontrado.setNombre(nuevoNombre);
            }

            System.out.print("Ingrese la nueva marca del producto (dejar en blanco para mantener): ");
            String nuevoProveedor = scanner.nextLine();
            if (!nuevoProveedor.isEmpty()) {
                productoEncontrado.setMarca(nuevoProveedor);
            }

            System.out.print("Ingrese el nuevo precio del producto (dejar en blanco para mantener): ");
            String nuevoPrecioStr = scanner.nextLine();
            if (!nuevoPrecioStr.isEmpty()) {
                float nuevoPrecio = Float.parseFloat(nuevoPrecioStr);
                productoEncontrado.setPrecio(nuevoPrecio);
            }

            sobrescribirArchivoConProductos(productos);
            System.out.println("Producto editado correctamente.");
        } else {
            System.out.println("No se encontró un producto con el ID especificado.");
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }
    
    public static void listarTiposProductos() {
        List<Producto> productos = cargarProductosDesdeArchivo();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("Lista de productos:");
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getId());
                System.out.println("Código: " + producto.getCodigo());
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Marca: " + producto.getMarca());
                System.out.println("Precio: " + producto.getPrecio());
                System.out.println("------------------------");
            }
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    private static int generarIdUnicoProducto() {
        List<Producto> productos = cargarProductosDesdeArchivo();
        int maxId = 0;

        for (Producto producto : productos) {
            if (producto.getId() > maxId) {
                maxId = producto.getId();
            }
        }

        return maxId + 1;
    }
    
    private static void guardarNuevoProductoEnArchivo(Producto producto) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("productos.txt", true))) {
            writer.println(producto.getId() + "," + producto.getCodigo() + "," + producto.getNombre() + ","
                    + producto.getMarca() + "," + producto.getPrecio());
        } catch (IOException e) {
            System.out.println("Error al guardar el producto en el archivo.");
            e.printStackTrace();
        }
    }
    
    public static List<Producto> cargarProductosDesdeArchivo() {
        List<Producto> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0]);
                String codigo = datos[1];
                String nombre = datos[2];
                String marca = datos[3];
                float precio = Float.parseFloat(datos[4]);

                Producto producto = new Producto(id, codigo, nombre, marca, precio);
                productos.add(producto);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los productos desde el archivo.");
            e.printStackTrace();
        }

        return productos;
    }
    
    private static void sobrescribirArchivoConProductos(List<Producto> productos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
                writer.println(producto.getId() + "," + producto.getCodigo() + "," + producto.getNombre() + ","
                        + producto.getMarca() + "," + producto.getPrecio());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los productos en el archivo.");
            e.printStackTrace();
        }
    }
    
    public int calcularStockActual() {
        List<IngresoProducto> ingresos = IngresoProducto.cargarIngresosDesdeArchivo();
        List<SalidaProducto> salidas = SalidaProducto.cargarSalidasDesdeArchivo();

        int stockActual = 0;

        for (IngresoProducto ingreso : ingresos) {
            if (ingreso.getProducto().equals(this.nombre)) {
                stockActual += ingreso.getCantidad();
            }
        }

        for (SalidaProducto salida : salidas) {
            if (salida.getProducto().equals(this.nombre)) {
                stockActual -= salida.getCantidad();
            }
        }

        return stockActual;
    }
    
    public static void verListaInventario() {
        List<Producto> productos = cargarProductosDesdeArchivo();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("Lista de inventario de productos:");
            for (Producto producto : productos) {
                int stockActual = producto.calcularStockActual();
                System.out.println("ID: " + producto.getId());
                System.out.println("Código: " + producto.getCodigo());
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Marca: " + producto.getMarca());
                System.out.println("Precio: " + producto.getPrecio());
                System.out.println("Stock actual: " + stockActual);
                System.out.println("------------------------");
            }
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
}
