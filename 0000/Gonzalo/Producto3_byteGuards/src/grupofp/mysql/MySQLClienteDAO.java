package grupofp.mysql;

import com.mysql.cj.xdevapi.Client;
import grupofp.dao.ClienteDAO;
import grupofp.dao.DAOException;
import grupofp.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MySQLClienteDAO implements ClienteDAO {
    final String INSERT = "INSERT INTO cliente (email, domicilio, nif, nombre) VALUES (?,?,?,?);";
    final String GETALL = "SELECT email, domicilio, nif, nombre FROM cliente";
    final String GETONE = "SELECT email, domicilio, nif, nombre FROM cliente WHERE email = ?";
    final String UPDATE = "UPDATE cliente SET email=?, domicilio=?, nif=?, nombre=? WHERE email=?";
    final String DELETE = "DELETE FROM cliente WHERE email=?";
    private Connection conn;

    private Cliente convertir (ResultSet rs) throws SQLException{
        String email = rs.getString("email");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String nombre = rs.getString("nombre");
        Cliente cliente = new Cliente(nombre, domicilio, nif, email) {
            @Override
            public float calcAnual() {
                return 0;
            }

            @Override
            public String tipoCliente() {
                return null;
            }

            @Override
            public float descuentoEnv() {
                return 0;
            }
        };

        return cliente;
    }
    @Override
    public void insertar(Cliente c) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(INSERT);
            stat.setString(1, c.getEmail());
            stat.setString(2, c.getDomicilio());
            stat.setString(3, c.getNif());
            stat.setString(4, c.getNombre());
            stat.executeUpdate();

            // Confirmar la transacción si va bien
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

    public void modificar(Cliente a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getEmail());
            stat.setString(2, a.getDomicilio());
            stat.setString(3, a.getNif());
            stat.setString(4, a.getNombre());
            stat.setString(5, a.getEmail()); // Utilizamos el email como criterio de modificación
            stat.executeUpdate();

            // Confirmar la transacción si va bien
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

            // Cerrar recursos
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
    public void eliminar(Cliente a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setString(1, a.getEmail()); // Utilizamos el email como criterio de eliminación
            stat.executeUpdate();

            // Confirmar la transacción si va bien
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

            // Cerrar recursos
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }

    public List<Cliente> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();

            while (rs.next()) {
                clientes.add(convertir(rs));
            }

            // Confirmar la transacción si va bien
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

            // Cerrar recursos
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

        return clientes;
    }


    @Override
    public Cliente obtener(String id) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente c = null;

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
            rs = stat.executeQuery();

            if (rs.next()) {
                c = convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado ese registro.");
            }

            // Confirmar la transacción si va bien
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

            // Cerrar recursos
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

        return c;
    }
}
