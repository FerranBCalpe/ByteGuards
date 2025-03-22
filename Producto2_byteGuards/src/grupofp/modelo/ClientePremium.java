package grupofp.modelo;

public class ClientePremium extends Cliente {
    public ClientePremium (String nombre, String domicilio, String nif, String email){
        super(nombre, domicilio, nif, email);
        this.cuotaAnual=30.0;
        this.descuentoEnvio=0.2;
    }
}
