package grupofp.vista;

import grupofp.controlador.Admin;
import grupofp.modelo.Articulo;
import grupofp.modelo.Cliente;
import grupofp.modelo.ClientePremium;
import grupofp.modelo.Pedido;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        // Crear el administrador
        Admin admin = new Admin();
        Menu menu = new Menu(admin); // Pasar admin a la vista
        menu.mostrarMenu(); // Ejecutar el menú

        //  Crear una clienta premium
        Cliente andrea = new ClientePremium("Andrea Saez", "Calle Falsa 123", "48399281L", "saezita@gmail.com");
        admin.agregarCliente(andrea);

        //  Crear un artículo (zapatillas) con código, descripción, precios y tiempo de preparación
        Articulo zapatillas = new Articulo("ZAP001", "Zapatillas deportivas", 34.3, 20.0, 2);
        admin.agregarArticulo(zapatillas);

        //  Calcular la fecha del pedido (hace 6 días)
        LocalDate fechaPedido = LocalDate.now().minusDays(6);

        //  Crear el pedido (2 pares de zapatillas)
        Pedido pedidoAndrea = new Pedido(1, andrea, 2, fechaPedido, zapatillas, false);
        admin.agregarPedido(pedidoAndrea);

        // Mostrar pedidos pendientes
        System.out.println("\n Pedidos Pendientes:");
        admin.mostrarPendientes();
    }
}
