package grupofp.vista;

import grupofp.controlador.Controlador;
import grupofp.controlador.ControladorArticulo;
import grupofp.controlador.ControladorCliente;
import grupofp.controlador.ControladorPedido;
import grupofp.modelo.Datos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Controlador controlador;
    public ControladorArticulo controladorArticulo;
    public ControladorCliente controladorCliente;
    public ControladorPedido controladorPedido;
    Scanner teclado = new Scanner(System.in);
    private MenuCliente menuCliente;
    private MenuArticulo menuArticulo;
    private MenuPedido menuPedido;


    public Menu() {
        Datos datos = new Datos();

        controladorArticulo = new ControladorArticulo();
        controladorCliente = new ControladorCliente();
        controladorPedido = new ControladorPedido();

        menuCliente = new MenuCliente(controladorCliente);
        menuArticulo = new MenuArticulo(controladorArticulo);
        menuPedido = new MenuPedido(controladorPedido);
    }

    public void inicio() {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Menú Cliente");
            System.out.println("2. Menú Articulo");
            System.out.println("3. Menú Pedidos");
            System.out.println("0. Salir");
            try {
                opcion = pedirOpcion();
                switch (opcion) {
                    case '1':
                        menuCliente.mostrarMenu(controladorCliente);
                        break;
                    case '2':
                        menuArticulo.mostrarMenu(controladorArticulo);
                        break;
                    case '3':
                        menuPedido.mostrarMenu(controladorPedido);
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


    private char ControlMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }
}
