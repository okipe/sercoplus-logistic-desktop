package sercoplus;

import java.util.Date;
import java.util.Scanner;
import modelo.IngresoProducto;
import modelo.SalidaProducto;
import modelo.Proveedor;
import modelo.Usuario;
import modelo.Producto;
import cli.screen;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * Description: Sistema de almacen Sercoplus
 * @author oscar
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("ALMACÉN SERCOPLUS");
        System.out.println("Autor: Oscar Israel Román Quispe - N00350433");
        System.out.println("Usuario: admin, Contraseña: password");
        System.out.println("=====================================================\n");
        System.out.println("Bienvenido al sistema de inventarios de almacén de Sercoplus \n");
        
        // Contraseña de usuario
        Usuario usuario = new Usuario(1, "admin",   "password");

        int intentos = 0;
        boolean accesoValido = false;
        
        while (intentos < 3 && !accesoValido) {
            System.out.print("Ingrese su nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();

            System.out.print("Ingrese su contraseña: ");
            String contrasenaIngresada = scanner.nextLine();

            if (usuario.getNombre().equals(nombreUsuario) && usuario.autenticaUsuario(contrasenaIngresada)) {
                accesoValido = true;
                System.out.println("Acceso válido. ¡Bienvenido, " + usuario.getNombre() + "!");
                // System.out.println("ADVERTENCIA: Solo funcionan las opciones del 7 al 13");
                cli.screen.clearscreen();
                mostrarMenu(scanner);
            } else {
                intentos++;
                System.out.println("Acceso inválido. Intento " + intentos + " de 3.");
            }
        }
        
        if (!accesoValido) {
            System.out.println("Ha excedido el número máximo de intentos. El sistema se cerrará.");
        }

        scanner.close();
    }
    
    public static void mostrarMenu(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\nGestión de productos");
            System.out.println("1. Ver lista de inventario de productos");
            System.out.println("2. Ingresar producto");
            System.out.println("3. Salida de producto");
            System.out.println("\nGestión de proveedores");
            System.out.println("4. Ver lista de proveedor");
            System.out.println("5. Ingresar nuevo proveedor");
            System.out.println("6. Editar proveedor");
            System.out.println("\nGestión de tipos de producto");
            System.out.println("7. Ver lista de tipos de producto");
            System.out.println("8. Agregar tipo de producto");
            System.out.println("9. Editar tipos de productos");
            System.out.println("\n10. Salir");
            System.out.print("\nDigite el número de la opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después de leer el número

            switch (opcion) {
                case 1:
                    System.out.println("\n=== Ver lista de productos ===");
                    cli.screen.clearscreen();
                    Producto.verListaInventario();
                    break;
                case 2:
                    System.out.println("\n=== Ingreso de producto ===");
                    cli.screen.clearscreen();
                    IngresoProducto.ingresarProducto(scanner);
                    break;
                case 3:
                    System.out.println("\n=== Retiro de producto ===");
                    cli.screen.clearscreen();
                    SalidaProducto.retirarProducto(scanner);
                    break;
                                
                case 4:
                    System.out.println("\n=== Ver lista de proveedores ===");
                    cli.screen.clearscreen();
                    Proveedor.listarProveedores();
                    break;
                case 5:
                    System.out.println("\n=== Ingreso de nuevo proveedor ===");
                    cli.screen.clearscreen();
                    Proveedor.ingresarNuevoProveedor(scanner);
                    break;
                case 6:
                    System.out.println("\n=== Edición de proveedor ===");
                    cli.screen.clearscreen();
                    Proveedor.editarProveedor(scanner);
                    break;
                case 7:
                    System.out.println("\n=== Ver lista de proveedores ===");
                    cli.screen.clearscreen();
                    Producto.listarTiposProductos();
                    break;
                case 8:
                    System.out.println("\n=== Agregando nuevo tipo de producto ===");
                    cli.screen.clearscreen();
                    Producto.agregarTipoProducto(scanner);
                    break;
                case 9:
                    System.out.println("\n=== Edición de tipo de producto ===");
                    cli.screen.clearscreen();
                    Producto.editarTipoProducto(scanner);
                    break;
                // Más opciones
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        } while (opcion != 10);
    }
    
    
}
