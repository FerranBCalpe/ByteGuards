package grupofp.vista;

import grupofp.controlador.Controlador;
import grupofp.controlador.ControladorCliente;
import grupofp.controlador.ControladorPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuCliente {

    private Controlador controlador;
    private ControladorCliente controladorCliente;
    Scanner teclado = new Scanner(System.in);

    public MenuCliente(ControladorCliente controladorCliente) {
        this.controladorCliente =  controladorCliente;

    }

    public void mostrarMenu(ControladorCliente controladorCliente) {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Mostrar Clientes Estándar");
            System.out.println("4. Mostrar Clientes Premium");
            System.out.println("0. Salir");
            try {
                opcion = pedirOpcion();
                switch (opcion) {
                    case '1':
                        addCliente();
                        break;
                    case '2':
                        mostrarClientes();
                        break;
                    case '3':
                        mostrarClientesEstandar();
                        break;
                    case '4':
                        mostrarClientesPremium();
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

    void mostrarClientes(){
        ArrayList<String> cliT = controladorCliente.recogerTodosClientes();
        for (String cli : cliT) {
            System.out.println(cli);
        }
    }

    void mostrarClientesEstandar(){
        ArrayList<String> cliE = controladorCliente.recogerClienteEstandar();
        for (String cli : cliE) {
            System.out.println(cli);
        }
    }

    void mostrarClientesPremium(){
        ArrayList<String> cliP = controladorCliente.recogerClientePremium();
        for (String cli : cliP) {
            System.out.println(cli);
        }
    }
    private char ControlMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }
}
