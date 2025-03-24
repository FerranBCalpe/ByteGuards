package grupofp.modelo;


// Se utiliza para definir atributos y métodos comunes a todos los tipos de clientes.
// Hay ciertos comportamientos como calcular la cuota anual, definir el tipo de cliente y
// calcular el descuento en el envío que varían según el tipo de cliente.

public abstract class Cliente {
    protected double cuotaAnual = 00.0;
    protected double descuentoEnvio = 0.0;

    private String nombre;
    private String domicilio;
    private String nif;
    private String email;


    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
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

}
