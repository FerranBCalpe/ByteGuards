package grupofp.vista;

import grupofp.controlador.Admin;
import java.util.Scanner;

public class Menu {
    private Scanner teclado;
    private Admin admin;

    public Menu(Admin admin) {
        this.admin = admin;
        this.teclado = new Scanner(System.in);
    }

    public void mostrarMenu() {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("\n--- Menú Principal ---");
            // Orden: Artículo, Cliente, Pedido
            System.out.println("1. Agregar Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("3. Agregar Cliente");
            System.out.println("4. Mostrar Clientes");
            System.out.println("5. Crear Pedido");
            System.out.println("6. Mostrar Pedidos Pendientes");
            System.out.println("7. Mostrar Pedidos Enviados");
            System.out.println("8. Eliminar Pedido");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");

            opcion = pedirOpcionMenu();

            switch (opcion) {
                case '1':
                    admin.agregarArticulo(teclado);
                    break;
                case '2':
                    admin.mostrarArticulos();
                    break;
                case '3':
                    admin.agregarCliente(teclado);
                    break;
                case '4':
                    admin.mostrarClientes();
                    break;
                case '5':
                    admin.crearPedido(teclado);
                    break;
                case '6':
                    admin.mostrarPendientes();
                    break;
                case '7':
                    admin.mostrarEnviados();
                    break;
                case '8':
                    System.out.print("Introduce el número de pedido a eliminar: ");
                    int numeroPedido = teclado.nextInt();
                    teclado.nextLine(); // Limpiar buffer
                    admin.eliminarPedido(numeroPedido);
                    break;
                case '9':
                    salir = true;
                    System.out.println("Saliendo de la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                    break;
            }
        } while (!salir);
    }

    /**
     * Método auxiliar para leer la opción del menú.
     */
    private char pedirOpcionMenu() {
        String resp = teclado.nextLine().trim();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }
}
