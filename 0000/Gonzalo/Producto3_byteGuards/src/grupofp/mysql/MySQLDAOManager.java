package grupofp.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a una base de datos MySQL.
 * Proporciona métodos para conectar y desconectar la base de datos.
 */
public class MySQLDAOManager {

    // Objeto Connection activo
    protected Connection conexion;

    // Parámetros de conexión a la base de datos
    private final String url = "jdbc:mysql://localhost:3306/OnlineStore_ByteGuards?useSSL=false&serverTimezone=Europe/Madrid";
    private final String user = "root";
    private final String password = "1234";
    private final String driver = "com.mysql.cj.jdbc.Driver";

    // Conexión activa (reutilizable)
    Connection cx;

    /**
     * Constructor por defecto.
     */
    public MySQLDAOManager(){
    }

    /**
     * Establece una conexión con la base de datos MySQL.
     *
     * @return Objeto Connection si la conexión fue exitosa.
     * @throws RuntimeException si no se puede cargar el driver o conectar a la base de datos.
     */
    public Connection conectar(){
        try {
            // Carga del driver JDBC necesario para MySQL
            Class.forName(driver);

            // Establece la conexión usando los datos definidos
            cx = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos MySQL");

            // Si hay un error, se informa y se lanza una excepción
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos:");
            throw new RuntimeException(e);
        }
        return cx;
    }

    /**
     * Cierra la conexión con la base de datos si está activa.
     *
     * @throws SQLException si ocurre un error al cerrar la conexión.
     */
    public void desconectar() throws SQLException{
        if(cx != null){
            if(!cx.isClosed()){
                cx.close();
                System.out.println("Se ha desconectado de la BBDD");
            }
        }
    }
}
