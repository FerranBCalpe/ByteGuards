package grupofp.controlador;

import grupofp.modelo.Datos;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControladorCliente {
    private Datos datos;

    public ControladorCliente() {
        datos = new Datos ();
    }
    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }


    public void entradaCliente(String nombre, String domicilio, String nif, String email, Float descuento) throws Controlador.NIFValidationException, Controlador.EmailValidationException {

        if (nif.length() >= 9) {
            throw new Controlador.NIFValidationException("El NIF no puede tener más de 9 dígitos.");
        }

        // Validación del correo electrónico
        if (email == null) {
            throw new Controlador.EmailValidationException("El correo electrónico no puede ser nulo");
        }

        if (!email.contains("@")) {
            throw new Controlador.EmailValidationException("El correo electrónico debe contener '@'");
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

}
