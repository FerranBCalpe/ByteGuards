package grupofp.modelo;

public class ClientePremium extends Cliente{

    private float descuento;
    public ClientePremium(String nombre, String domicilio, String nif, String email, float descuento) {
        super(nombre, domicilio, nif, email);
        this.descuento=descuento;
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

    public String tipoCliente(){
        return "Cliente Premium";
    }
    public float descuentoEnv(){
        return getDescuento();
    }

    public  float calcAnual(){
        float calcA = 30;
        return calcA;
    }
}
