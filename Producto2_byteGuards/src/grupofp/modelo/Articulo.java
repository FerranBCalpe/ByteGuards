package grupofp.modelo;

public class Articulo {

    private String codigoArticulo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacion;

    // constructor
    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.codigoArticulo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    //mutartors_setters?

    // accesor methods - getters

    public String getCodigoArticulo(){
        return codigoArticulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public double getPrecioVenta(){
        return precioVenta;
    }

    public double getGastosEnvio(){
        return gastosEnvio;
    }

    public int getTiempoPreparacion(){
        return tiempoPreparacion;
    }



}
