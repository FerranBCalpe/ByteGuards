package grupofp.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pedido {
    private int numPedido;
    private int cantidad;
    private LocalDateTime fecha;
    private Cliente cliente;
    private Articulo articulo;

    public Pedido (int numPedido, int cantidad, LocalDateTime fecha, Cliente cliente, Articulo articulo){
        this.numPedido = numPedido;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.cliente = cliente;
        this.articulo = articulo;
    }

    public Pedido (int numPedido, int cantidad, LocalDateTime fecha, Articulo articulo){
        this.numPedido = numPedido;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.articulo = articulo;
    }
    public Pedido(int numPedido, int cantidad, LocalDateTime fecha) {
        this.numPedido = numPedido;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public boolean pedidoEnviado(){
        LocalDateTime hoy = LocalDateTime.now();
        if((getFecha().plusMinutes(getArticulo().getTiempoPreparacion())).isBefore(hoy)){
            return true;

        }
        return false;
    }
    public String comprobar(){
        String enviado = "";
        if(pedidoEnviado()){
            enviado = "El pedido ya se ha enviado";
        }else{
            enviado = "El pedido está pendiente de envío";
        }
        return enviado;
    }

    public float precioEnvio(){
        float costeTotal = ((articulo.getPrecio() + articulo.getGastosEnvio())) - ((articulo.getPrecio() + articulo.getGastosEnvio()) * getCliente().descuentoEnv())/100;
        return costeTotal;
    }
    @Override
    public String toString() {
        return "Pedido{" +
                "Numero del Pedido=" + numPedido +
                ", Cantidad del Pedido=" + cantidad +
                ", Fecha y Hora del Pedido=" + fecha +
                ", Cliente=" + cliente +
                ", Artículo=" + articulo +
                ", Precio Total=" + precioEnvio() +
                ", Estado del Pedido=" + comprobar() +
                '}';
    }
}
