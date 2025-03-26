package grupofp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * La clase Datos es el núcleo central del modelo de la aplicación, encargada de gestionar
 * y almacenar las colecciones dinámicas de datos, incluyendo artículos, clientes y pedidos.
 * Actúa como el intermediario entre el controlador y el resto del modelo, proporcionando
 * métodos para añadir, modificar, eliminar y consultar los datos a través de sus listas:
 * - ListaArticulos para gestionar los artículos.
 * - ListaClientes para gestionar los clientes.
 * - ListaPedidos para gestionar los pedidos.
 * Cada una de estas listas se implementa utilizando una estructura dinámica basada en ArrayList,
 * lo que permite un manejo flexible y eficiente del almacenamiento de datos, redimensionándose
 * automáticamente según la necesidad. Esta abstracción facilita la reutilización y el mantenimiento
 * del código, al centralizar la gestión de la información en una única clase.
 */
public class Datos {
    private ListaArticulos listaArticulos;
    private ListaClientes listaClientes;
    private ListaPedidos listaPedidos;


    public Datos() {
        listaArticulos = new ListaArticulos();
        listaClientes = new ListaClientes();
        listaPedidos = new ListaPedidos();

    }

    public ListaArticulos getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ListaArticulos listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public ListaClientes getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ListaClientes listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ListaPedidos getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(ListaPedidos listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public void addArticulo(String id, String descripcion, float precio, float gastosEnvio, int tiempoPreparacion) {

        listaArticulos.add(new Articulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion));
    }
    public ArrayList recorrerTodosArticulos(){
        ArrayList<String> arrArticulos = new ArrayList<>();
        for(Articulo a : listaArticulos.lista){
            arrArticulos.add(a.toString());
        }
        return arrArticulos;
    }

    public void addCliente(String nombre, String domicilio, String nif, String email, Float descuento) {
        if (descuento != null) {
            listaClientes.add(new ClientePremium(nombre, domicilio, nif, email, descuento));
        } else {
            listaClientes.add(new ClienteEstandar(nombre, domicilio, nif, email));
        }

    }

    public ArrayList recorrerTodosClientes(){
        ArrayList<String> arrClientes = new ArrayList<>();
        for(Cliente listaClientes1 : listaClientes.lista){
            arrClientes.add(listaClientes1.toString());
        }
        return arrClientes;
    }
    public ArrayList recorrerClienteE() {
        ArrayList<String> arrClienteEstandar = new ArrayList<>();
        for (Cliente listaClientes1 : listaClientes.lista) {
            if (listaClientes1 instanceof ClienteEstandar) {
                arrClienteEstandar.add(listaClientes1.toString());
            }
        }
        return arrClienteEstandar;
    }

    public ArrayList recorrerClienteP() {
        ArrayList<String> arrClientePremium = new ArrayList<>();
        for (Cliente listaClientes1 : listaClientes.lista) {
            if (listaClientes1 instanceof ClientePremium) {
                arrClientePremium.add(listaClientes1.toString());
            }

        }
        return arrClientePremium;
    }

    public boolean addPedido(int numPedido, int cantidad, LocalDateTime fecha, String email, String id) {
        int contenido = -1;
        if (existeCliente(email)) {
            contenido = dameCliente(email);
            listaPedidos.add(new Pedido(numPedido, cantidad, fecha, getListaClientes().getAt(contenido), dameArticulo(id)));
            return true;
        }

        if(!existeCliente(email)){
            listaPedidos.add(new Pedido(numPedido, cantidad, fecha, dameArticulo(id)));
            return false;
        }
        return false;
    }


    public Articulo dameArticulo(String id){
        Articulo articulo = new Articulo();
        for(Articulo art : listaArticulos.lista){
            if(id.equals(art.getcodigoArticulo())){
                articulo = art;
            }
        }
        return articulo;
    }

    boolean existeCliente(String email) {
        for (Cliente cli : listaClientes.lista) {
            if (cli.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public int dameCliente(String email){
        int contenido = 0;
        for(Cliente cli : listaClientes.lista){
            if(email.equals(cli.getEmail())){
                return contenido;
            }
            contenido++;
        }
        return contenido;
    }

    public void addClientePedido(){
        int lastIC = listaClientes.lista.size() -1;
        int lastIP = listaPedidos.lista.size()- 1;
        int numPedido = listaPedidos.getAt(lastIP).getNumPedido();
        int cantidad = listaPedidos.getAt(lastIP).getCantidad();
        LocalDateTime fecha = listaPedidos.getAt(lastIP).getFecha();
        Articulo a = listaPedidos.getAt(lastIP).getArticulo();
        listaPedidos.borrar(listaPedidos.getAt(lastIP));
        listaPedidos.add(new Pedido(numPedido, cantidad, fecha, listaClientes.getAt(lastIC), a));

    }

    public void borrarPedido(int numPedido){
        for(Pedido p : listaPedidos.lista){
            if(numPedido == p.getNumPedido() && p.pedidoEnviado() == true){
                listaPedidos.borrar(p);
                break;
            }
        }
    }

    public ArrayList<String> pendientes(){
        ArrayList<String> arrPedido = new ArrayList<>();
        for(Pedido p : listaPedidos.lista){
            if(p.pedidoEnviado() == false){
                arrPedido.add(p.toString());
            }
        }
        return arrPedido;
    }

    public ArrayList<String> filtroPendiente(String email){
        ArrayList<String> filtro = new ArrayList<>();
        for(Pedido p : listaPedidos.lista){
            if(p.getCliente().getEmail().equals(email) && p.pedidoEnviado() == false){
                filtro.add(p.toString());
            }
        }
        return filtro;
    }

    public ArrayList<String> enviados(){
        ArrayList<String> arrPedido = new ArrayList<>();
        for(Pedido p : listaPedidos.lista){
            if(p.pedidoEnviado() == true){
                arrPedido.add(p.toString());
            }
        }
        return arrPedido;
    }
    public ArrayList<String> filtroEnviado(String email){
        ArrayList<String> filtro = new ArrayList<>();
        for(Pedido p : listaPedidos.lista){
            if(p.getCliente().getEmail().equals(email) && p.pedidoEnviado() == true){
                filtro.add(p.toString());
            }
        }
        return filtro;
    }
}
