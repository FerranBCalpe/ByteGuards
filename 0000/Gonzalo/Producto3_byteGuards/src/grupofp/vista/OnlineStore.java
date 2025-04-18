package grupofp.vista;

/**
 * Clase principal de la aplicación OnlineStore.
 * Desde aquí se lanza la interfaz de usuario (menú principal).
 */
public class OnlineStore {
    public static void main(String[] args) {

        // Se crea una instancia del menú de gestión
        Menu gestion = new Menu();

        // Se inicia el menú principal de la aplicación
        gestion.inicio();

    }
}