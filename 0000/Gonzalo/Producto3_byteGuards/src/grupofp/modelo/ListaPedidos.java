package grupofp.modelo;

public class ListaPedidos extends Lista<Pedido> {
    private Pedido pedido;

    public ListaPedidos(Pedido pedido){
        this.pedido=pedido;
    }
    public ListaPedidos(){}

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
