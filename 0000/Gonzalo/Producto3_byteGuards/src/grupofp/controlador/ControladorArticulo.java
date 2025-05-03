package grupofp.controlador;

import grupofp.modelo.Datos;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControladorArticulo {

    private Datos datos;

    public ControladorArticulo() {
        datos = new Datos ();
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }
    public void entradaArticulo(String id, String descripcion, float precio, float gastosEnvio, int tiempoPreparacion) {
        datos.addArticulo(id, descripcion, precio, gastosEnvio, tiempoPreparacion);

    }
    public ArrayList recogerTodosArticulos(){
        ArrayList<String> arrArticulos = new ArrayList<>();
        arrArticulos= datos.recorrerTodosArticulos();
        return arrArticulos;
    }
}
