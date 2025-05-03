package grupofp.controlador;

import grupofp.modelo.Datos;

import java.time.LocalDateTime;
import java.util.ArrayList;



public class Controlador{
    private Datos datos;

    public Controlador() {
        datos = new Datos ();
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }





    public boolean existeC(String email){
        if(datos.existeCliente(email)){
            return true;
        }
        return false;
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
