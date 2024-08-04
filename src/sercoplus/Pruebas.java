/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sercoplus;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author oscar
 */
public class Pruebas {
    public static void main(String[] args) {
        // Lista de proveedores disponibles
        List<String> proveedores = Arrays.asList("Proveedor 1", "Proveedor 2", "Proveedor 3");

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        System.out.println("Proveedores disponibles:");
        for (int i = 0; i < proveedores.size(); i++) {
            System.out.println((i + 1) + ". " + proveedores.get(i));
        }

        System.out.print("Ingrese el número del proveedor o escriba el nombre completo: ");
        String entrada = scanner.nextLine();

        // Verificar si el usuario ingresó un número o un nombre
        String proveedorSeleccionado;
        try {
            int indice = Integer.parseInt(entrada) - 1;
            if (indice >= 0 && indice < proveedores.size()) {
                proveedorSeleccionado = proveedores.get(indice);
            } else {
                System.out.println("Número de proveedor inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            // El usuario ingresó un nombre
            if (proveedores.contains(entrada)) {
                proveedorSeleccionado = entrada;
            } else {
                System.out.println("Proveedor no encontrado.");
                return;
            }
        }

        System.out.println("Proveedor seleccionado: " + proveedorSeleccionado);
    }
}
