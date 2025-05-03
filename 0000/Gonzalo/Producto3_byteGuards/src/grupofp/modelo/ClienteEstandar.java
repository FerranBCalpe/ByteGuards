package grupofp.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "clienteestandar")
@PrimaryKeyJoinColumn(name = "idCliente")
public class ClienteEstandar extends Cliente {

    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public ClienteEstandar() {
        super();
    }

    @Override
    public String toString() {
        return "Cliente Estándar{" +
                "Nombre del Cliente Estándar: " + getNombre() +
                " Domicilio del Cliente Estándar: " + getDomicilio() +
                " NIF del Cliente Estándar: " + getNif() +
                " Email del Cliente Estándar: " + getEmail() +
                '}';
    }

    @Override
    public String tipoCliente() {
        return "Cliente Estandar";
    }

    @Override
    public float descuentoEnv() {
        return 1;
    }

    @Override
    public float calcAnual() {
        return 0;
    }
}
