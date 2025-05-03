package grupofp.vista;

import grupofp.controlador.Controlador;
import grupofp.controlador.ControladorArticulo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuArticulo {

    private Controlador controlador;
    private ControladorArticulo controladorArticulo;
    Scanner teclado = new Scanner(System.in);

    public MenuArticulo(ControladorArticulo controladorArticulo) {
        this.controladorArticulo =  controladorArticulo;
    }


    public void mostrarMenu(ControladorArticulo controladorArticulo) {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Añadir Articulo");
            System.out.println("2. Mostrar Articulos");
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

        controladorArticulo.entradaArticulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion);
        System.out.println("Se ha añadido el nuevo Articulo");
    }

    void mostrarArticulos(){
        ArrayList<String> aArt = controladorArticulo.recogerTodosArticulos();
        for (String a : aArt) {
            System.out.println(a);
        }
    }
    private char ControlMenu() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }
}
