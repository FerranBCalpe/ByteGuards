package grupofp.modelo;

public class ClienteEstandar extends Cliente{
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
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

    public String tipoCliente(){
        return "Cliente Estandar";
    }

    public float descuentoEnv(){
        float descuentoEnv = 1;
        return descuentoEnv;
    }
    public  float calcAnual(){
        float calcA = 0;
        return calcA;
    }
}
