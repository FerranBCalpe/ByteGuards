package grupofp.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private Connection connection;

    public MySQLConnector() {
        String url = "jdbc:mysql://localhost:3306/onlinestore_byteguards?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos MySQL.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
