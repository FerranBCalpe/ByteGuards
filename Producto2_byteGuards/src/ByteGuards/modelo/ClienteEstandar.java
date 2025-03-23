package grupofp.modelo;

public class ClienteEstandar extends Cliente {

    public ClienteEstandar (String nombre, String domicilio, String nif, String email){
        super (nombre,domicilio,nif,email);
        this.cuotaAnual = 0.0;
        this.descuentoEnvio = 0.0;
    }
}
