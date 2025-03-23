package grupofp.vista;

import grupofp.controlador.Controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Controlador controlador;
    Scanner teclado = new Scanner(System.in);

    public Menu() {
        controlador = new Controlador();
    }

    public void inicio() {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Añadir Articulo");
            System.out.println("2. Mostrar Articulos");
            System.out.println("3. Añadir Cliente");
            System.out.println("4. Mostrar Clientes");
            System.out.println("5. Mostrar Clientes Estándar");
            System.out.println("6. Mostrar Clientes Premium");
            System.out.println("7. Añadir Pedido");
            System.out.println("8. Eliminar Pedido");
            System.out.println("9. Mostrar todos los Pedidos Pendientes");
            System.out.println("A. Filtrar Pedidos Pendientes por Cliente");
            System.out.println("B. Mostrar todos los Pedidos Enviados");
            System.out.println("C. Filtrar Pedidos Enviados por Cliente");
            System.out.println("0. Salir");
            try {
                opcion = pedirOpcion();
                switch (opcion) {
                    case '1':
                        addArticulo();
                        break;
                    case '2':
                        mostrarArticulos();
                        break;
                    case '3':
                        addCliente();
                        break;
                    case '4':
                        mostrarClientes();
                        break;
                    case '5':
                        mostrarClientesEstandar();
                        break;
                    case '6':
                        mostrarClientesPremium();
                        break;
                    case '7':
                        addPedido();
                        break;
                    case '8':
                        eliminarPedido();
                        break;
                    case '9':
                        mostrarTodosPendientes();
                        break;
                    case 'A': case 'a':
                        filtrarClientePend();
                        break;
                    case 'B': case 'b':
                        mostrarTodosEnviados();
                        break;
                    case 'C': case 'c':
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


    void addArticulo(){
        System.out.printf("Añade el codigo del Artículo: ");
        String id = teclado.nextLine();
        System.out.println("Descripcion del Articulo: ");
        String descripcion = teclado.nextLine();
        System.out.println("Precio del Articulo: ");
        float precio = teclado.nextFloat();
        teclado.nextLine();
        System.out.println("Gastos de envío del Articulo: ");
        float gastosEnvio = teclado.nextFloat();
        teclado.nextLine();
        System.out.printf("Tiempo de preparación del Articulo: ");
        int tiempoPreparacion = teclado.nextInt();
        teclado.nextLine();

        controlador.entradaArticulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion);
        System.out.println("Se ha añadido el nuevo Articulo");
    }

    void mostrarArticulos(){
        ArrayList<String> aArt = controlador.recogerTodosArticulos();
        for (String a : aArt) {
            System.out.println(a);
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
                    controlador.entradaCliente(nombre, domicilio, nif, email, null);
                    System.out.println("Se ha añadido un nuevo cliente Estandar");
                    break;
                case "2":
                    System.out.println("Descuento del cliente Premium: ");
                    float descuento = teclado.nextFloat();
                    teclado.nextLine();
                    controlador.entradaCliente(nombre, domicilio, nif, email, descuento);
                    System.out.println("Se ha añadido un nuevo cliente Premium");
                    break;
            }
        } catch (Controlador.NIFValidationException e) {
            System.out.println("Error al ingresar el NIF del Cliente: " + e.getMessage());
        } catch (Controlador.EmailValidationException e) {
            System.out.println("Error al ingresar el Email del Cliente: " + e.getMessage());
        }
    }

    void mostrarClientes(){
        ArrayList<String> cliT = controlador.recogerTodosClientes();
        for (String cli : cliT) {
            System.out.println(cli);
        }
    }

    void mostrarClientesEstandar(){
        ArrayList<String> cliE = controlador.recogerClienteEstandar();
        for (String cli : cliE) {
            System.out.println(cli);
        }
    }

    void mostrarClientesPremium(){
        ArrayList<String> cliP = controlador.recogerClientePremium();
        for (String cli : cliP) {
            System.out.println(cli);
        }
    }

    public void addPedido(){
        System.out.printf("Número de Pedido: ");
        int numPedido = teclado.nextInt();
        teclado.nextLine();
        System.out.println("Cantidad del Pedido: ");
        int cantidad = teclado.nextInt();
        teclado.nextLine();
        System.out.println("Fecha y hora del Pedido: (dd/MM/yyyy/HH/mm)");
        String f = teclado.nextLine();
        LocalDateTime fecha = LocalDateTime.parse(f, DateTimeFormatter.ofPattern("dd/MM/yyyy/HH/mm"));
        System.out.println(fecha);
        System.out.println("Email del Cliente: ");
        String email = teclado.nextLine();
        System.out.println("Codigo del Articulo: ");
        String id = teclado.nextLine();
        boolean existe = controlador.entradaPedido(numPedido, cantidad, fecha, email, id);

        if(!existe){
            addCliente();
            controlador.addClientePedido();
        }
        System.out.println("Se ha añadido el nuevo Pedido");
    }

    public void eliminarPedido(){
        System.out.printf("Indica el numero de pedido que quiere eliminar: ");
        int numPedido = teclado.nextInt();
        teclado.nextLine();
        controlador.eliminarPedido(numPedido);
    }

    public void mostrarTodosPendientes() {
        ArrayList<String> aTodosPend = controlador.todosPendientes();
        for (String tP : aTodosPend) {
            System.out.println(tP);
        }
    }

    public void filtrarClientePend(){
        System.out.println("Introduce el Email del Cliente: ");
        String email = teclado.nextLine();
        ArrayList<String> fClientePendiente = controlador.filtrarClientePendiente(email);
        for (String f : fClientePendiente) {
            System.out.println(f);
        }
    }

    public void mostrarTodosEnviados() {
        ArrayList<String> aTodosEnv = controlador.todosEnviados();
        for (String tP : aTodosEnv) {
            System.out.println(tP);
        }
    }

    public void filtrarClienteEnv(){
        System.out.println("Introduce el Email del Cliente: ");
        String email = teclado.nextLine();
        ArrayList<String> fClienteEnviado = controlador.filtrarClienteEnviado(email);
        for (String f : fClienteEnviado) {
            System.out.println(f);
        }
    }
    /**
     * Lee una línea completa de entrada del usuario y retorna el primer carácter.
     * Se utiliza para capturar la opción seleccionada en un menú.
     */
    private char ControlMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }
}
