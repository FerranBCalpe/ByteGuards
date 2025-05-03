package grupofp.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulo")
public class Articulo {
    @Id
    @Column(name = "idArticulo")
    private String codigoArticulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "precio")
    private float precio;
    @Column(name = "gastosEnvio")
    private float gastosEnvio;
    @Column(name = "tiempoPreparacion")
    private int tiempoPreparacion;
    public Articulo(){}
    public Articulo(String idArticulo, String descripcion, float precio, float gastosEnvio, int tiempoPreparacion) {
        this.codigoArticulo = idArticulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getcodigoArticulo() {
        return codigoArticulo;
    }

    public void setcodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(float gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "Codigo del Articulo='" + codigoArticulo + '\'' +
                ", Descripcion del Articulo='" + descripcion + '\'' +
                ", Precio del Articulo=" + precio +
                ", Gastos de envio del Articulo=" + gastosEnvio +
                ", Tiempo de Preparacion del Articulo=" + tiempoPreparacion +
                '}';
    }

}