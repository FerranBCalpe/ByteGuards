package grupofp.controlador;


import grupofp.modelo.Datos;
import java.time.LocalDateTime;
import java.util.ArrayList;



public class Controlador{
    private Datos datos; // Instancia de la clase Datos, que maneja la lógica de los datos

    public Controlador() {
        datos = new Datos ();
    } // Se inicializa la instancia de Datos


    // Métodos getter y setter para la propiedad 'datos'
    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    /**
     * Metodo para agregar un artículo al sistema.
     * @param id ID del artículo
     * @param descripcion Descripción del artículo
     * @param precio Precio del artículo
     * @param gastosEnvio Gastos de envío del artículo
     * @param tiempoPreparacion Tiempo de preparación del artículo
     */
    public void entradaArticulo(String id, String descripcion, float precio, float gastosEnvio, int tiempoPreparacion) {
        datos.addArticulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion);

    }

    /**
     * Metodo para recoger todos los artículos.
     * @return Lista de artículos
     */
    public ArrayList recogerTodosArticulos(){
        ArrayList<String> arrArticulos = new ArrayList<>();
        arrArticulos= datos.recorrerTodosArticulos();
        return arrArticulos;
    }
    public void entradaCliente(String nombre, String domicilio, String nif, String email, Float descuento) throws NIFValidationException, EmailValidationException {

        if (nif.length() >= 9) {
            throw new NIFValidationException("El NIF no puede tener más de 9 dígitos.");
        }

        // Validación del correo electrónico
        if (email == null) {
            throw new EmailValidationException("El correo electrónico no puede ser nulo");
        }

        if (!email.contains("@")) {
            throw new EmailValidationException("El correo electrónico debe contener '@'");
        }

        if (descuento != null) {
            datos.addCliente(nombre, domicilio, nif, email, descuento);
        } else {
            datos.addCliente(nombre, domicilio, nif, email, null);
        }
    }


    public ArrayList recogerTodosClientes(){
        ArrayList<String> arrClientes = new ArrayList<>();
        arrClientes= datos.recorrerTodosClientes();
        return arrClientes;
    }
    public ArrayList recogerClienteEstandar(){
        ArrayList<String> arrClienteEstandar = new ArrayList<>();
        arrClienteEstandar = datos.recorrerClienteE();
        return arrClienteEstandar;
    }

    public ArrayList recogerClientePremium(){
        ArrayList<String> arrClientePremium = new ArrayList<>();
        arrClientePremium = datos.recorrerClienteP();
        return arrClientePremium;
    }

    public boolean entradaPedido(int numPedido, int cantidad, LocalDateTime fecha, String email, String id) {
        boolean existe = datos.addPedido(numPedido, cantidad, fecha, email, id);
        return existe;
    }

    public void eliminarPedido(int numPedido){
        datos.borrarPedido(numPedido);
    }


    public ArrayList<String> todosPendientes() {
        return datos.filtroPendiente();
    }

    public ArrayList<String> todosEnviados() {
        return datos.filtroEnviado();
    }

    public ArrayList<String> filtrarClientePendiente(String email){
        return datos.filtroPendiente(email);
    }

    public ArrayList<String> filtrarClienteEnviado(String email){
        return datos.filtroEnviado(email);
    }


    public boolean existeC(String email){
        if(datos.existeCliente(email)){
            return true;
        }
        return false;
    }


    public class EmailValidationException extends Exception {
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
