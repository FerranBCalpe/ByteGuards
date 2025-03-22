package grupofp.controlador;

import grupofp.modelo.Articulo;
import grupofp.modelo.Cliente;
import grupofp.modelo.ClientePremium;
import grupofp.modelo.Pedido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class Admin {

  private List<Cliente> clientes;
  private List<Articulo> articulos;
  private List<Pedido> pedidos;

    // constructor

   public Admin(){
       this.clientes = new ArrayList<>();
       this.articulos = new ArrayList<>();
       this.pedidos = new ArrayList<>();
   }

    //mutators_setters?
    // accesor methods - getters

    //metodos-acciones

    public void agregarCliente(Cliente cliente){
       clientes.add(cliente);
    }
    public void mostrarclientes(){
       for (Cliente cliente : clientes){
           System.out.println("Nombre: " + cliente.getNombre() + " | Email: " + cliente.getEmail());

       }
    }
    public void agregarcliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void mostrarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println("Nombre: " + cliente.getNombre() + " | Email: " + cliente.getEmail());
        }
    }

    public void agregarArticulo(Articulo articulo) {
        articulos.add(articulo);
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void eliminarPedido(int numeroPedido) {
        pedidos.removeIf(pedido -> pedido.getNumeroPedido() == numeroPedido);
    }

    public void mostrarPendientes() {
        System.out.println("Pedidos Pendientes:");
        boolean hayPendientes = false;

        for (Pedido pedido : pedidos) {
            if (!pedido.seHaEnviado()) {
                Articulo articulo = pedido.getArticulo(); //esta ñapa se tiene que poner porque si no el numero de artículo no se encuentra.
                // Suponiendo que `estaEnviado()` verifica si ya se envió
                System.out.println("grupofp.modelo.Pedido #" + pedido.getNumeroPedido() +
                        " - grupofp.modelo.Cliente: " + pedido.getCliente().getNombre() +
                        "\n   N#Artículo: " + articulo.getCodigoArticulo() +
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
    public void mostrarEnviados(){
            System.out.println("Pedidos Enviados:");
            boolean hayEnviados = false;

            for (Pedido pedido : pedidos) {
                if (pedido.seHaEnviado()) {
                    System.out.println("grupofp.modelo.Pedido #" + pedido.getNumeroPedido() + " - grupofp.modelo.Cliente: " + pedido.getCliente().getNombre());
                    hayEnviados = true;
                }
            }

            if (!hayEnviados) {
                System.out.println("No hay pedidos enviados.");
            }
        }

    // Métodos getter si en algún momento necesitamos acceder a las listas desde fuera
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes); // Devolvemos una copia para evitar modificaciones externas
    }

    public List<Articulo> getArticulos() {
        return new ArrayList<>(articulos);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
        }






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

    public void agregarArticulo(Scanner scanner) {
        System.out.println("Introduce el código del artículo:");
        String codigo = scanner.nextLine();
        System.out.println("Introduce la descripción:");
        String descripcion = scanner.nextLine();
        System.out.println("Introduce el precio base:");
        double precioVenta = scanner.nextDouble();
        System.out.println("Introduce los gastos de envio:");
        double gastosEnvio = scanner.nextDouble();
        System.out.println("Introduce el tiempo de preparación:");
        int tiempoPreparacion = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        agregarArticulo(nuevoArticulo);
        System.out.println("Artículo agregado con éxito.");
    }

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

        LocalDate fechaPedido = LocalDate.now();
        Pedido nuevoPedido = new Pedido(numeroPedido, cliente, cantidad, fechaPedido, articulo);
        agregarPedido(nuevoPedido);
        System.out.println("Pedido creado con éxito.");
    }


}
