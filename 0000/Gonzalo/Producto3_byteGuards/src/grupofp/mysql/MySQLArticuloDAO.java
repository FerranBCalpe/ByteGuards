package grupofp.mysql;

import grupofp.dao.ArticuloDAO;
import grupofp.dao.DAOException;
import grupofp.modelo.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySQLArticuloDAO implements ArticuloDAO {
    final String INSERT = "INSERT INTO articulo (idArticulo, descripcion, precio, gastosEnvio, tiempoPreparacion) VALUES (?,?,?,?,?);";
    final String GETALL = "SELECT idArticulo, descripcion, precio, gastosEnvio, tiempoPreparacion FROM articulo";
    final String GETONE = "SELECT idArticulo, descripcion, precio, gastosEnvio, tiempoPreparacion FROM articulo WHERE idArticulo = ?";
    final String UPDATE = "UPDATE articulo SET descripcion=?, precio=?, gastosEnvio=?, tiempoPreparacion=? WHERE idArticulo=?";
    final String DELETE = "DELETE FROM articulo WHERE idArticulo=?";

    private Connection conn;

    public MySQLArticuloDAO(Connection conn){
        this.conn = conn;
    }

    public MySQLArticuloDAO() {

    }
    // Metodo auxiliar para convertir un ResultSet en un objeto Artículo
    private Articulo convertir (ResultSet rs) throws SQLException{
        String idArticulo = rs.getString("idArticulo");
        String descripcion = rs.getString("descripcion");
        Float precio = rs.getFloat("precio");
        Float gastosEnvio = rs.getFloat("gastosEnvio");
        int tiempoPreparacion = rs.getInt("tiempoPreparacion");
        Articulo articulo = new Articulo(idArticulo, descripcion, precio, gastosEnvio, tiempoPreparacion);
        return articulo;
    }

    // Metodo para insertar un artículo en la base de datos
    @Override
    public void insertar(Articulo a) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null; // No uses la variable de instancia, crea una local.

        try {
            conn = new MySQLDAOManager().conectar(); // Se obtiene una conexión nueva

            conn.setAutoCommit(false);  // Se desactiva el auto-commit para control de transacción

            stat = conn.prepareStatement(INSERT);
            stat.setString(1, a.getcodigoArticulo());
            stat.setString(2, a.getDescripcion());
            stat.setFloat(3, a.getPrecio());
            stat.setFloat(4, a.getGastosEnvio());
            stat.setInt(5, a.getTiempoPreparacion());

            stat.executeUpdate(); // Se ejecuta la sentencia
            conn.commit();  // Se confirma la transacción

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

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }

    // Metodo para modificar un artículo existente
    @Override
    public void modificar(Articulo a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getDescripcion());
            stat.setFloat(2, a.getPrecio());
            stat.setFloat(3, a.getGastosEnvio());
            stat.setFloat(4, a.getTiempoPreparacion());
            stat.setString(5, a.getcodigoArticulo());
            stat.executeUpdate();


            conn.commit(); // Se confirma si ha ido bien

        } catch (SQLException ex) {
            // Se revierte en caso de error
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

    // Metodo para eliminar un artículo
    @Override
    public void eliminar(Articulo a) throws DAOException {
        PreparedStatement stat = null;
        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setString(1, a.getcodigoArticulo()); // Utilizamos el email como criterio de eliminación
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
    // Metodo para obtener todos los artículos
    @Override
    public List<Articulo> obtenerTodos() throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Articulo> articulos = new ArrayList<>();

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();

            // Se añaden los artículos a la lista
            while (rs.next()) {
                articulos.add(convertir(rs));
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
        return articulos;
    }

    // Metodo para obtener un artículo por su ID
    @Override
    public Articulo obtener(String idArticulo) throws DAOException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Articulo a = null;

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(GETONE);
            stat.setString(1, idArticulo);
            rs = stat.executeQuery();

            if (rs.next()) {
                a = convertir(rs); // Se encuentra y convierte el artículo
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

        return a;
    }
}
