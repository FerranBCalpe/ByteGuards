package grupofp.modelo;

import java.util.ArrayList;

public class ListaClientes extends Lista<Cliente> {
    private Cliente cliente;

    public ListaClientes(Cliente cliente){
        this.cliente=cliente;
    }

    public ListaClientes(ArrayList lista){
        super(lista);
    }

    public ListaClientes(){}

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
