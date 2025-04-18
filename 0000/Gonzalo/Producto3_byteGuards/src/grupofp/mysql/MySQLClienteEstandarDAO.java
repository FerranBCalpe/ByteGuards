package grupofp.mysql;

import grupofp.dao.ClienteEstandarDAO;
import grupofp.dao.DAOException;
import grupofp.modelo.ClienteEstandar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySQLClienteEstandarDAO implements ClienteEstandarDAO {
    final String INSERT = "INSERT INTO cliente_estandar (email_estandar, nombre, nif, domicilio) VALUES (?,?,?,?);";
    final String GETALL = "SELECT email_estandar, nombre, nif, domicilio FROM cliente_estandar";
    final String UPDATE = "UPDATE cliente_estandar SET nombre=?, nif=?, domicilio=? WHERE email_estandar=?";
    final String DELETE = "DELETE FROM cliente_estandar WHERE email_estandar=?";
    private Connection conn;

    public MySQLClienteEstandarDAO(Connection conn){
        this.conn = conn;
    }

    public MySQLClienteEstandarDAO() {

    }

    // Inserta un cliente estándar en la base de datos
    @Override
    public void insertar(ClienteEstandar a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilita auto-commit para usar transacciones
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(INSERT);
            stat.setString(1, a.getEmail());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getNif());
            stat.setString(4, a.getDomicilio());
            stat.executeUpdate();

            // Si va bien, confirmamos la transacción
            conn.commit();

        } catch (SQLException ex) {
            // Rollback si hay error
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }
            throw new DAOException("Error en SQL", ex);

        } finally {
            // Restauramos el auto-commit y cerramos recursos
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la BBDD");
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

    // Modifica un cliente estándar existente
    @Override
    public void modificar(ClienteEstandar a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getNif());
            stat.setString(3, a.getDomicilio());
            stat.setString(4, a.getEmail());
            stat.executeUpdate();

            conn.commit();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }
            throw new DAOException("Error en SQL al modificar", ex);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la BBDD");
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

    // Elimina un cliente estándar de la base de datos
    @Override
    public void eliminar(ClienteEstandar a) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setString(1, a.getEmail());
            stat.executeUpdate();

            conn.commit();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL al eliminar", ex);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la BBDD");
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

    // Convierte un ResultSet en un objeto ClienteEstandar
    private ClienteEstandar convertir (ResultSet rs) throws SQLException{
        String email = rs.getString("email_estandar");
        String nombre = rs.getString("nombre");
        String nif = rs.getString("nif");
        String domicilio = rs.getString("domicilio");
        ClienteEstandar ce = new ClienteEstandar(nombre, domicilio, nif, email);

        return ce;
    }

    // Devuelve todos los clientes estándar de la base de datos
    @Override
    public List<ClienteEstandar> obtenerTodos() throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<ClienteEstandar> clienteEstandars = new ArrayList<>();
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                clienteEstandars.add(convertir(rs)); // Convertimos cada fila a objeto
            }

            conn.commit();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL", ex);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Se ha desconectado de la BBDD");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
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
        return clienteEstandars;
    }
    // Este metodo no está implementado, pero existe porque está definido en la interfaz
    @Override
    public ClienteEstandar obtener(Long id) {
        return null;
    }

}
