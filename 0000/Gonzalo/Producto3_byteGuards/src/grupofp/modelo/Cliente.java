package grupofp.modelo;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "idCliente")
    private int idCliente;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "nif")
    private String nif;
    @Column(name = "email")
    private String email;

    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    public Cliente() {

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Nombre del Cliente='" + nombre + '\'' +
                ", Domicilio del Cliente='" + domicilio + '\'' +
                ", NIF del Cliente='" + nif + '\'' +
                ", Email del Cliente='" + email + '\'' +
                '}';
    }


    public abstract float calcAnual();
    public abstract String tipoCliente();

    public abstract float descuentoEnv();

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
