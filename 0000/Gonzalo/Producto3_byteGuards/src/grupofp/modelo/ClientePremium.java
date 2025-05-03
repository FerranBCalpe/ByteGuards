package grupofp.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "clientepremium")
@PrimaryKeyJoinColumn(name = "idCliente")
public class ClientePremium extends Cliente {

    @Column(name = "descuento")
    private float descuento;

    public ClientePremium(String nombre, String domicilio, String nif, String email, float descuento) {
        super(nombre, domicilio, nif, email);
        this.descuento = descuento;
    }

    public ClientePremium() {
        super();
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Cliente Premium{" +
                "Nombre del Cliente Premium: " + getNombre() +
                " Domicilio del Cliente Premium: " + getDomicilio() +
                " NIF del Cliente Premium: " + getNif() +
                " Email del Cliente Premium: " + getEmail() +
                " Descuento del Cliente Premium: " + descuento +
                '}';
    }

    @Override
    public String tipoCliente() {
        return "Cliente Premium";
    }

    @Override
    public float descuentoEnv() {
        return getDescuento();
    }

    @Override
    public float calcAnual() {
        return 30;
    }
}

