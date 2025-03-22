package grupofp.controlador;

import grupofp.modelo.Articulo;
import grupofp.modelo.Cliente;
import grupofp.modelo.Pedido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {

    private List<Cliente> clientes;
    private List<Articulo> articulos;
    private List<Pedido> pedidos;

    // Constructor
    public Admin() {
        this.clientes = new ArrayList<>();
        this.articulos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // ======================
    // MÉTODOS PARA CLIENTES
    // ======================

    /**
     * Agrega un cliente a la lista.
     */
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Agrega un cliente solicitando datos por consola.
     */
    public void agregarCliente(Scanner scanner) {
        System.out.println("Introduce el nombre del cliente:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce la dirección:");
        String domicilio = scanner.nextLine();
        System.out.println("Introduce el DNI:");
        String nif = scanner.nextLine();
        System.out.println("Introduce el email:");
        String email = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, domicilio, nif, email);
        agregarCliente(nuevoCliente);
        System.out.println("Cliente agregado con éxito.");
    }

    /**
     * Muestra la lista de clientes registrados.
     */
    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("--- Lista de Clientes ---");
            for (Cliente cliente : clientes) {
                System.out.println("Nombre: " + cliente.getNombre() +
                        " | Domicilio: " + cliente.getDomicilio() +
                        " | DNI: " + cliente.getNif() +
                        " | Email: " + cliente.getEmail());
            }
        }
    }

    /**
     * Retorna una copia de la lista de clientes.
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    // ======================
    // MÉTODOS PARA ARTÍCULOS
    // ======================

    /**
     * Agrega un artículo a la lista.
     */
    public void agregarArticulo(Articulo articulo) {
        articulos.add(articulo);
    }

    /**
     * Agrega un artículo solicitando datos por consola.
     */
    public void agregarArticulo(Scanner scanner) {
        System.out.println("Introduce el código del artículo:");
        String codigo = scanner.nextLine();
        System.out.println("Introduce la descripción:");
        String descripcion = scanner.nextLine();
        System.out.println("Introduce el precio base:");
        double precioVenta = scanner.nextDouble();
        System.out.println("Introduce los gastos de envío:");
        double gastosEnvio = scanner.nextDouble();
        System.out.println("Introduce el tiempo de preparación (días):");
        int tiempoPreparacion = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        agregarArticulo(nuevoArticulo);
        System.out.println("Artículo agregado con éxito.");
    }

    /**
     * Muestra la lista de artículos registrados.
     */
    public void mostrarArticulos() {
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            System.out.println("--- Lista de Artículos ---");
            for (Articulo articulo : articulos) {
                System.out.println("Código: " + articulo.getCodigoArticulo() +
                        " | Descripción: " + articulo.getDescripcion() +
                        " | Precio Base: " + articulo.getPrecioVenta() +
                        " | Gastos de Envío: " + articulo.getGastosEnvio() +
                        " | Tiempo de Preparación: " + articulo.getTiempoPreparacion() + " días");
            }
        }
    }

    /**
     * Retorna una copia de la lista de artículos.
     */
    public List<Articulo> getArticulos() {
        return new ArrayList<>(articulos);
    }

    // ======================
    // MÉTODOS PARA PEDIDOS
    // ======================

    /**
     * Agrega un pedido a la lista.
     */
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    /**
     * Crea un pedido solicitando los datos por consola.
     */
    public void crearPedido(Scanner scanner) {
        System.out.println("Introduce el número de pedido:");
        int numeroPedido = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        System.out.println("Introduce el DNI del cliente:");
        String dniCliente = scanner.nextLine();
        Cliente cliente = clientes.stream()
                .filter(c -> c.getNif().equals(dniCliente))
                .findFirst()
                .orElse(null);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Introduce el código del artículo:");
        String codigoArticulo = scanner.nextLine();
        Articulo articulo = articulos.stream()
                .filter(a -> a.getCodigoArticulo().equals(codigoArticulo))
                .findFirst()
                .orElse(null);

        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        System.out.println("Introduce la cantidad:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer



        //añadir se ha enviado este pedido???


        LocalDate fechaPedido = LocalDate.now();
        Pedido nuevoPedido = new Pedido(numeroPedido, cliente, cantidad, fechaPedido, articulo, false);



        System.out.println("Se ha enviado este producto? S/N");
        String respuesta = scanner.nextLine(); // Leemos la respuesta como String
        boolean enviado = respuesta.equalsIgnoreCase("S"); // Si es "S" (mayúscula o minúscula), enviado es true
        System.out.println("Enviado: " + enviado);


        agregarPedido(nuevoPedido);
        System.out.println("Pedido creado con éxito.");
    }

    /**
     * Elimina un pedido dado su número.
     */
    public void eliminarPedido(int numeroPedido) {
        boolean eliminado = pedidos.removeIf(pedido -> pedido.getNumeroPedido() == numeroPedido);
        if (eliminado) {
            System.out.println("Pedido #" + numeroPedido + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún pedido con el número " + numeroPedido + ".");
        }
    }

    /**
     * Muestra los pedidos pendientes (que no han sido enviados).
     */
    public void mostrarPendientes() {
        System.out.println("--- Pedidos Pendientes ---");
        boolean hayPendientes = false;

        for (Pedido pedido : pedidos) {
            if (!pedido.seHaEnviado()) {
                Articulo articulo = pedido.getArticulo();
                System.out.println("Pedido #" + pedido.getNumeroPedido() +
                        " - Cliente: " + pedido.getCliente().getNombre() +
                        "\n   Código Artículo: " + articulo.getCodigoArticulo() +
                        "\n   Descripción: " + articulo.getDescripcion() +
                        "\n   Cantidad: " + pedido.getCantidad() +
                        "\n   Precio Total: " + pedido.CalcularPrecioTotal() + "€");
                hayPendientes = true;
            }
        }

        if (!hayPendientes) {
            System.out.println("No hay pedidos pendientes.");
        }
    }

    /**
     * Muestra los pedidos enviados.
     */
    public void mostrarEnviados() {
        System.out.println("--- Pedidos Enviados ---");
        boolean hayEnviados = false;

        for (Pedido pedido : pedidos) {
            if (pedido.seHaEnviado()) {
                System.out.println("Pedido #" + pedido.getNumeroPedido() +
                        " - Cliente: " + pedido.getCliente().getNombre());
                hayEnviados = true;
            }
        }

        if (!hayEnviados) {
            System.out.println("No hay pedidos enviados.");
        }
    }

    /**
     * Retorna una copia de la lista de pedidos.
     */
    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }
}
