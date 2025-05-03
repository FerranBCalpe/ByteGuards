package grupofp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilidad {
    protected Connection conexion;
    private final String url = "jdbc:mysql://localhost:3306/onlinestore_byteguards";
    private final String user = "root";
    private final String password = "1234";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    public Utilidad(){

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
            }
        }
    }
}
