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
    public Pedido(int numeroPedido, Cliente cliente, int cantidad, LocalDate fechaPedido, Articulo articulo, Boolean enviado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fechaPedido = fechaPedido;
        this.articulo = articulo;
        this.enviado = enviado;
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


    // FALTA REVISAR ESTA PARTE
    public boolean PuedeCancelarse(LocalDate newDate) {
        LocalDate fechaLimite = fechaPedido.plusDays(articulo.getTiempoPreparacion());
        return newDate.isBefore(fechaLimite);
    }

}
