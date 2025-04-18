package grupofp.mysql;

import grupofp.dao.ClienteDAO;
import grupofp.dao.DAOException;
import grupofp.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implementación concreta de ClienteDAO para MySQL
public class MySQLClienteDAO implements ClienteDAO {
    final String INSERT = "INSERT INTO cliente (email, domicilio, nif, nombre) VALUES (?,?,?,?);";
    final String GETALL = "SELECT email, domicilio, nif, nombre FROM cliente";
    final String GETONE = "SELECT email, domicilio, nif, nombre FROM cliente WHERE email = ?";
    final String UPDATE = "UPDATE cliente SET email=?, domicilio=?, nif=?, nombre=? WHERE email=?";
    final String DELETE = "DELETE FROM cliente WHERE email=?";
    private Connection conn;

    // Metodo auxiliar para convertir un ResultSet en un objeto Cliente
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

    // Inserta un cliente en la base de datos
    @Override
    public void insertar(Cliente c) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false); // Comenzar transacción manual

            stat = conn.prepareStatement(INSERT);
            stat.setString(1, c.getEmail());
            stat.setString(2, c.getDomicilio());
            stat.setString(3, c.getNif());
            stat.setString(4, c.getNombre());
            stat.executeUpdate();  // Ejecutar inserción

            conn.commit();  // Confirmar transacción

        } catch (SQLException ex) {
            // Revertir en caso de error
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException("Error en rollback", e);
            }

            throw new DAOException("Error en SQL", ex);

        } finally {
            // Restaurar estado y cerrar recursos
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

    // Modifica un cliente existente
    public void modificar(Cliente a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getEmail());
            stat.setString(2, a.getDomicilio());
            stat.setString(3, a.getNif());
            stat.setString(4, a.getNombre());
            stat.setString(5, a.getEmail()); // Criterio: email actual
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
    // Elimina un cliente de la base de datos
    @Override
    public void eliminar(Cliente a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setString(1, a.getEmail()); // Criterio: email
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

    // Recupera todos los clientes de la base de datos
    public List<Cliente> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();

            while (rs.next()) {
                clientes.add(convertir(rs)); // Convertir cada fila en Cliente
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
        return clientes;
    }

    // Recupera un cliente específico por email
    @Override
    public Cliente obtener(String id) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Cliente c = null;

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
            rs = stat.executeQuery();

            if (rs.next()) {
                c = convertir(rs);  // Convertir a objeto Cliente
            } else {
                throw new DAOException("No se ha encontrado ese registro.");
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
        return c;
    }
}
