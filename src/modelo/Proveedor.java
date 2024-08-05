// Proveedor.java

package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Proveedor {
    private int id;
    private String ruc;
    private String razonSocial;
    private String telefono;
    private String direccion;
    private String email;

    public Proveedor(int id, String ruc, String razonSocial, String telefono, String direccion, String email) {
        this.id = id;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    public static void listarProveedores() {
        List<Proveedor> proveedores = cargarProveedoresDesdeArchivo();

        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            System.out.println("Lista de proveedores:");
            for (Proveedor proveedor : proveedores) {
                System.out.println("ID: " + proveedor.getId());
                System.out.println("RUC: " + proveedor.getRuc());
                System.out.println("Razón Social: " + proveedor.getRazonSocial());
                System.out.println("Teléfono: " + proveedor.getTelefono());
                System.out.println("Dirección: " + proveedor.getDireccion());
                System.out.println("Email: " + proveedor.getEmail());
                System.out.println("------------------------");
            }
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    public static List<Proveedor> cargarProveedoresDesdeArchivo() {
        List<Proveedor> proveedores = new ArrayList<>();

        // Ruta del archivo
        String carpeta = "data/";
        String nombreArchivo = "proveedores.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificar si existe el archivo
        File archivo = new File(rutaCompleta);
        if (!archivo.exists()) {
            System.out.println("El archivo de ingresos no existe.");
            return proveedores; // Retorna una lista vacía si el archivo no existe
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0]);
                String ruc = datos[1];
                String razonSocial = datos[2];
                String telefono = datos[3];
                String direccion = datos[4];
                String email = datos[5];

                Proveedor proveedor = new Proveedor(id, ruc, razonSocial, telefono, direccion, email);
                proveedores.add(proveedor);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los proveedores desde el archivo.");
            e.printStackTrace();
        }
        
        return proveedores;
    }
    
    public static void ingresarNuevoProveedor(Scanner scanner) {
        System.out.print("Ingrese el RUC del proveedor: ");
        String ruc = scanner.nextLine();

        System.out.print("Ingrese la razón social del proveedor: ");
        String razonSocial = scanner.nextLine();

        System.out.print("Ingrese el teléfono del proveedor: ");
        String telefono = scanner.nextLine();

        System.out.print("Ingrese la dirección del proveedor: ");
        String direccion = scanner.nextLine();

        System.out.print("Ingrese el email del proveedor: ");
        String email = scanner.nextLine();

        int id = generarIdUnicoProveedor();

        Proveedor proveedor = new Proveedor(id, ruc, razonSocial, telefono, direccion, email);
        guardarProveedorEnArchivo(proveedor);

        System.out.println("Proveedor ingresado correctamente.\n");
        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
        // return proveedor;
    }
    
    private static int generarIdUnicoProveedor() {
        List<Proveedor> proveedores = cargarProveedoresDesdeArchivo();
        int maxId = 0;

            for (Proveedor proveedor : proveedores) {
                if (proveedor.getId() > maxId) {
                    maxId = proveedor.getId();
                }
            }
		return maxId + 1;// Ejemplo: retorna siempre 1 (ajusta esto según tus necesidades)
	}

    private static void guardarProveedorEnArchivo(Proveedor proveedor) {
        // Carpeta para guardar el archivo
        String carpeta = "data/";
        String nombreArchivo = "proveedores.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificamos si existe la carpeta
        File directorio = new File(rutaCompleta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta, true))) {
            writer.println(proveedor.getId() + "," + proveedor.getRuc() + "," + proveedor.getRazonSocial() + ","
                    + proveedor.getTelefono() + "," + proveedor.getDireccion() + "," + proveedor.getEmail());
        } catch (IOException e) {
            System.out.println("Error al guardar el proveedor en el archivo.");
            e.printStackTrace();
        }
    }
    
    public static void editarProveedor(Scanner scanner) {
        List<Proveedor> proveedores = cargarProveedoresDesdeArchivo();

        System.out.print("Ingrese el ID del proveedor a editar: ");
        int idProveedor = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después de leer el ID

        Proveedor proveedorEncontrado = null;
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getId() == idProveedor) {
                proveedorEncontrado = proveedor;
                break;
            }
        }

        if (proveedorEncontrado != null) {
            System.out.println("Datos actuales del proveedor:");
            System.out.println("RUC: " + proveedorEncontrado.getRuc());
            System.out.println("Razón Social: " + proveedorEncontrado.getRazonSocial());
            System.out.println("Teléfono: " + proveedorEncontrado.getTelefono());
            System.out.println("Dirección: " + proveedorEncontrado.getDireccion());
            System.out.println("Email: " + proveedorEncontrado.getEmail());

            System.out.print("Ingrese el nuevo RUC del proveedor (dejar en blanco para mantener): ");
            String nuevoRuc = scanner.nextLine();
            if (!nuevoRuc.isEmpty()) {
                proveedorEncontrado.setRuc(nuevoRuc);
            }

            System.out.print("Ingrese la nueva Razón Social del proveedor (dejar en blanco para mantener): ");
            String nuevaRazonSocial = scanner.nextLine();
            if (!nuevaRazonSocial.isEmpty()) {
                proveedorEncontrado.setRazonSocial(nuevaRazonSocial);
            }

            System.out.print("Ingrese el nuevo Teléfono del proveedor (dejar en blanco para mantener): ");
            String nuevoTelefono = scanner.nextLine();
            if (!nuevoTelefono.isEmpty()) {
                proveedorEncontrado.setTelefono(nuevoTelefono);
            }

            System.out.print("Ingrese la nueva Dirección del proveedor (dejar en blanco para mantener): ");
            String nuevaDireccion = scanner.nextLine();
            if (!nuevaDireccion.isEmpty()) {
                proveedorEncontrado.setDireccion(nuevaDireccion);
            }

            System.out.print("Ingrese el nuevo Email del proveedor (dejar en blanco para mantener): ");
            String nuevoEmail = scanner.nextLine();
            if (!nuevoEmail.isEmpty()) {
                proveedorEncontrado.setEmail(nuevoEmail);
            }

            guardarProveedoresEnArchivo(proveedores);
            System.out.println("Proveedor editado correctamente.");
        } else {
            System.out.println("No se encontró un proveedor con el ID especificado.");
        }

        System.out.print("Presione Enter para regresar al menú principal...");
        scanner.nextLine();
    }
    
    private static void guardarProveedoresEnArchivo(List<Proveedor> proveedores) {
        // Carpeta para guardar el archivo
        String carpeta = "data/";
        String nombreArchivo = "proveedores.txt";
        String rutaCompleta = carpeta + nombreArchivo;
        
        // Verificamos si existe la carpeta
        File directorio = new File(rutaCompleta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta))) {
            for (Proveedor proveedor : proveedores) {
                writer.println(proveedor.getId() + "," + proveedor.getRuc() + "," + proveedor.getRazonSocial() + ","
                        + proveedor.getTelefono() + "," + proveedor.getDireccion() + "," + proveedor.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los proveedores en el archivo.");
            e.printStackTrace();
        }
}
}
