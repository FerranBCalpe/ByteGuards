package grupofp.modelo;

public class Cliente {
    protected double cuotaAnual = 00.0;
    protected double descuentoEnvio = 0.0;

    private String nombre;
    private String domicilio;
    private String nif;
    private String email;



    // constructor
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }
    //mutators_setters?

    // accesor methods? - getters

    public String getEmail(){
        return email;
    }

    public String getDomicilio() {return domicilio;}

    public String getNombre(){
        return nombre;
    }

    public String getNif(){
        return nif;
    }

    public double getCuotaAnual(){
        return cuotaAnual;
    }

    public double getDescuentoEnvio(){
        return descuentoEnvio;
    }




}
