package grupofp.vista;

import grupofp.controlador.Controlador;
import grupofp.controlador.ControladorArticulo;
import grupofp.controlador.ControladorCliente;
import grupofp.controlador.ControladorPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPedido {

    public Controlador controlador;
    public ControladorPedido controladorPedido;
    public ControladorCliente controladorCliente;
    Scanner teclado = new Scanner(System.in);

    public MenuPedido(ControladorPedido controladorPedido) {
        this.controladorPedido = new ControladorPedido();
    }

    public void mostrarMenu(ControladorPedido controladorPedido) {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Añadir Pedido");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostrar todos los Pedidos Pendientes");
            System.out.println("4. Filtrar Pedidos Pendientes por Cliente");
            System.out.println("5. Mostrar todos los Pedidos Enviados");
            System.out.println("6. Filtrar Pedidos Enviados por Cliente");
            System.out.println("0. Salir");
            try {
                opcion = pedirOpcion();
                switch (opcion) {
                    case '1':
                        addPedido();
                        break;
                    case '2':
                        eliminarPedido();
                        break;
                    case '3':
                        mostrarTodosPendientes();
                        break;
                    case '4':
                        filtrarClientePend();
                        break;
                    case '5':
                        mostrarTodosEnviados();
                        break;
                    case '6':
                        filtrarClienteEnv();
                        break;
                    case '0':
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Introduce una opción existente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Anade un número o letra que sea válida.");
            }
        } while (!salir);
    }

    char pedirOpcion() {
        String resp;
        System.out.println("Elige una opción: ");
        resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }

    /**
     * Añade un nuevo pedido pidiendo todos los datos necesarios.
     * Si el cliente no existe, lo crea antes de añadir el pedido.
     */
    public void addPedido(){
        System.out.printf("Número de pedido: ");
        int numPedido = teclado.nextInt();
        teclado.nextLine();
        System.out.println("Cantidad: ");
        int cantidad = teclado.nextInt();
        teclado.nextLine();


        System.out.println("Fecha y hora del pedido: (dd/MM/yyyy/HH/mm)");
        String f = teclado.nextLine();
        LocalDateTime fecha = LocalDateTime.parse(f, DateTimeFormatter.ofPattern("dd/MM/yyyy/HH/mm"));
        System.out.println(fecha);
        System.out.println("Email del Cliente: ");
        String email = teclado.nextLine();
        System.out.println("Id de Articulo: ");
        String id = teclado.nextLine();

        // Si el cliente no existe, se le solicita añadirlo
        if(!controladorPedido.existeC(email)){
            addCliente();
        }
        controladorPedido.entradaPedido(numPedido, cantidad, fecha, email, id);

        System.out.println("Se ha añadido el nuevo Pedido");
    }

    /**
     * Elimina un pedido solicitando su número.
     */
    public void eliminarPedido(){
        System.out.printf("Indica el numero de pedido que quiere eliminar: ");
        int numPedido = teclado.nextInt();
        teclado.nextLine();
        controladorPedido.eliminarPedido(numPedido);
    }
    public void mostrarTodosPendientes() {
        ArrayList<String> aTodosPend = controladorPedido.todosPendientes();
        for (String tP : aTodosPend) {
            System.out.println(tP);
        }
    }

    public void filtrarClientePend(){
        System.out.println("Introduce el Email del Cliente: ");
        String email = teclado.nextLine();
        ArrayList<String> fClientePendiente = controladorPedido.filtrarClientePendiente(email);
        for (String f : fClientePendiente) {
            System.out.println(f);
        }
    }

    public void mostrarTodosEnviados() {
        ArrayList<String> aTodosEnv = controladorPedido.todosEnviados();
        for (String tP : aTodosEnv) {
            System.out.println(tP);
        }
    }

    public void filtrarClienteEnv(){
        System.out.println("Introduce el Email del Cliente: ");
        String email = teclado.nextLine();
        ArrayList<String> fClienteEnviado = controladorPedido.filtrarClienteEnviado(email);
        for (String f : fClienteEnviado) {
            System.out.println(f);
        }
    }
    void addCliente() {
        try {
            System.out.printf("Añade nombre del Cliente: ");
            String nombre = teclado.nextLine();
            System.out.println("Domicilio del Cliente: ");
            String domicilio = teclado.nextLine();
            System.out.println("NIF del Cliente: ");
            String nif = teclado.nextLine();
            System.out.println("Email del Cliente: ");
            String email = teclado.nextLine();

            String tipo;
            do {
                System.out.println("(1) Estandar, (2) Premium");
                tipo = teclado.nextLine();
            } while (!"12".contains(tipo));

            switch (tipo) {
                case "1":
                    controladorCliente.entradaCliente(nombre, domicilio, nif, email, null);
                    System.out.println("Se ha añadido un nuevo cliente Estandar");
                    break;
                case "2":
                    System.out.println("Descuento del cliente Premium: ");
                    float descuento = teclado.nextFloat();
                    teclado.nextLine();
                    controladorCliente.entradaCliente(nombre, domicilio, nif, email, descuento);
                    System.out.println("Se ha añadido un nuevo cliente Premium");
                    break;
            }
        } catch (Controlador.NIFValidationException e) {
            System.out.println("Error al ingresar el NIF del Cliente: " + e.getMessage());
        } catch (Controlador.EmailValidationException e) {
            System.out.println("Error al ingresar el Email del Cliente: " + e.getMessage());
        }
    }

    private char ControlMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }

}
