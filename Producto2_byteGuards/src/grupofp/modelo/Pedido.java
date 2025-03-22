package grupofp.modelo;

import java.time.LocalDate;

public class Pedido {

    private int numeroPedido;
    private Cliente cliente;
    private int cantidad;
    private LocalDate fechaPedido;
    private Articulo articulo;
    private boolean enviado;


    //constructor
    public Pedido(int numeroPedido, Cliente cliente, int cantidad, LocalDate fechaPedido, Articulo articulo) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fechaPedido = fechaPedido;
        this.articulo = articulo;
    }

    //accesor methods - getters



    public Articulo getArticulo(){
        return articulo;
    }
    public int getNumeroPedido(){
        return numeroPedido;
    }

    public int getCantidad(){return cantidad;}

    public Cliente getCliente(){
        return cliente;
    }

    public boolean seHaEnviado(){
        return enviado;
    }

    public void marcarComoEnviado(){
        this.enviado=true;
    }

    // métodos-acciones

    public double CalcularPrecioTotal(){
        double descuento = cliente.getDescuentoEnvio();
        return(articulo.getPrecioVenta() * cantidad) * (1 - descuento);
    }

    public boolean PuedeCancelarse(LocalDate newDate){
        // fecha del momento
        LocalDate fechaPedidoLocal= fechaPedido.plusDays(articulo.getTiempoPreparacion());
        // fecha máxima para cancelar (preparción+ fecha pedido > fecha del momento)
        LocalDate fechaLimite = fechaPedidoLocal.plusDays(articulo.getTiempoPreparacion());

        return newDate.isBefore(fechaLimite);
    }


}
