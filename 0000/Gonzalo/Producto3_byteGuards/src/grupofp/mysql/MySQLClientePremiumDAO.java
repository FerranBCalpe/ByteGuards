package grupofp.mysql;

import grupofp.dao.ClientePremiumDAO;
import grupofp.dao.DAOException;
import grupofp.modelo.ClientePremium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MySQLClientePremiumDAO implements ClientePremiumDAO {
    final String INSERT = "INSERT INTO cliente_premium (email_premium, nombre, nif, domicilio, descuento) VALUES (?,?,?,?,?);";
    final String GETALL = "SELECT email_premium, nombre, nif, domicilio, descuento FROM cliente_premium";
    final String UPDATE = "UPDATE cliente_premium SET nombre=?, nif=?, domicilio=?, descuento=? WHERE email_premium=?";
    final String DELETE = "DELETE FROM cliente_premium WHERE email_premium=?";
    private Connection conn;
    public MySQLClientePremiumDAO(Connection conn) {
        this.conn = conn;
    }

    public MySQLClientePremiumDAO() {

    }

    @Override
    public void insertar(ClientePremium a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(INSERT);
            stat.setString(1, a.getEmail());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getNif());
            stat.setString(4, a.getDomicilio());
            stat.setFloat(5, a.getDescuento());
            stat.executeUpdate();

            // Confirmar la transacción si todo va bien
            conn.commit();

        } catch (SQLException ex) {
            // Hacer rollback en caso de error
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL", ex);

        } finally {
            // Restaurar auto-commit y cerrar recursos
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la bbdd");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }


    @Override
    public void modificar(ClientePremium a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getNif());
            stat.setString(3, a.getDomicilio());
            stat.setFloat(4, a.getDescuento());
            stat.setString(5, a.getEmail());
            stat.executeUpdate();

            // Confirmar la transacción si todo va bien
            conn.commit();

        } catch (SQLException ex) {
            // Hacer rollback en caso de error
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL al modificar", ex);

        } finally {
            // Restaurar auto-commit y cerrar recursos
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la bbdd");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }


    @Override
    public void eliminar(ClientePremium a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setString(1, a.getEmail());
            stat.executeUpdate();

            // Confirmar la transacción si todo va bien
            conn.commit();

        } catch (SQLException ex) {
            // Hacer rollback en caso de error
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL al eliminar", ex);

        } finally {
            // Restaurar auto-commit y cerrar recursos
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la bbdd");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }

    private ClientePremium convertir (ResultSet rs) throws SQLException{
        String email = rs.getString("email_premium");
        String nombre = rs.getString("nombre");
        String nif = rs.getString("nif");
        String domicilio = rs.getString("domicilio");
        Float descuento = rs.getFloat("descuento");
        ClientePremium cp = new ClientePremium(nombre, domicilio, nif, email, descuento);

        return cp;
    }
    @Override
    public List<ClientePremium> obtenerTodos() throws DAOException {
        conn = new MySQLDAOManager().conectar();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<ClientePremium> clientePremiums = new ArrayList<>();
        try{
            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()){
                clientePremiums.add(convertir(rs));
            }
        } catch (SQLException ex){
            throw new DAOException("Error en SQL", ex);
        } finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if (stat != null){
                try {
                    stat.close();
                } catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
        }try {
            conn.close();
            System.out.println("Se ha desconectado de la bbdd");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientePremiums;
    }

    @Override
    public ClientePremium obtener(Long id) {
        return null;
    }
}
