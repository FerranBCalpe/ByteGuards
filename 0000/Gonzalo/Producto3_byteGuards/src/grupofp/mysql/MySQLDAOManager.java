package grupofp.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOManager {
    protected Connection conexion;
    private final String url = "jdbc:mysql://localhost:3306/onlinestore_byteguards?useSSL=false&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    public MySQLDAOManager(){

    }

    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos MySQL");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos:");
            throw new RuntimeException(e);
        }
        return cx;
    }

    public void desconectar() throws SQLException{
        if(cx != null){
            if(!cx.isClosed()){
                cx.close();
                System.out.println("Se ha desconectado de la bbdd");
            }
        }
    }
}
