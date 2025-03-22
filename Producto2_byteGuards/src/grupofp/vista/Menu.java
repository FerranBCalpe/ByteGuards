package grupofp.vista;

import grupofp.controlador.Admin;
import java.util.Scanner;


public class Menu {
    Scanner teclado = new Scanner(System.in);
    private Admin admin;
    private Scanner scanner;

    public Menu(Admin admin) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Agregar Artículo");
            System.out.println("3. Crear Pedido");
            System.out.println("4. Mostrar Pedidos Pendientes");
            System.out.println("5. Salir de la aplicación");
            System.out.print("Elige una opción: ");
            opcion = demanarOpcionMenu();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case '1':
                    admin.agregarCliente(scanner);
                    break;
                case '2':
                    admin.agregarArticulo(scanner);
                    break;
                case '3':
                    admin.crearPedido(scanner);
                    break;
                case '4':
                    admin.mostrarPendientes();
                    break;
                case '5':
                    salir = true;
            }
        } while (!salir);
    }
    char demanarOpcionMenu() {
        String resp;
        System.out.print("Elige una opción (1,2,3,4,5 o 0): ");
        resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }
}
