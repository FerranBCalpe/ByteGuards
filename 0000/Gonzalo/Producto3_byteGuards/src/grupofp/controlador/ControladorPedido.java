package grupofp.controlador;

import grupofp.modelo.Datos;
import grupofp.modelo.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedido {

    private Datos datos;

    public ControladorPedido() {
        datos = new Datos ();
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    public boolean entradaPedido(int numPedido, int cantidad, LocalDateTime fecha, String email, String id) {
        boolean existe = datos.addPedido(numPedido, cantidad, fecha, email, id);
        return existe;
    }

    public void eliminarPedido(int numPedido){
        datos.borrarPedido(numPedido);
    }

    public ArrayList<String> todosPendientes(){
        ArrayList<String> arrTodosPendientes = new ArrayList<>();
        arrTodosPendientes = datos.pendientes();
        return arrTodosPendientes;
    }

    public ArrayList<String> todosEnviados(){
        ArrayList<String> arrTodosEnviados = new ArrayList<>();
        arrTodosEnviados = datos.enviados();
        return arrTodosEnviados;
    }

    public ArrayList<String> filtrarClientePendiente(String email) {
        ArrayList<String> arrFiltroCliente = new ArrayList<>();
        List<Pedido> pedidos = datos.filtroPendiente(email); // ahora devuelve List<Pedido>

        for (Pedido p : pedidos) {
            arrFiltroCliente.add(p.toString()); // o cualquier formato que necesites
        }

        return arrFiltroCliente;
    }


    public ArrayList<String> filtrarClienteEnviado(String email) {
        ArrayList<String> arrFiltroCliente = new ArrayList<>();
        List<Pedido> pedidos = datos.filtroEnviado(email);

        for (Pedido p : pedidos) {
            arrFiltroCliente.add(p.toString());
        }

        return arrFiltroCliente;
    }

    public boolean existeC(String email) {
        return datos.existeCliente(email);
    }




    public static class EmailValidationException extends Exception {
        public EmailValidationException(String message) {
            super(message);
        }
    }

    public static class NIFValidationException extends Exception {
        public NIFValidationException(String message) {
            super(message);
        }
    }
}


