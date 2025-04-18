package grupofp.mysql;

import grupofp.dao.DAOException;
import grupofp.dao.PedidoDAO;
import grupofp.modelo.Pedido;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class MySQLPedidoDAO implements PedidoDAO {
    final String INSERT = "INSERT INTO pedido (num_pedido, cantidad, fecha, fk_cliente, fk_articulo) VALUES (?,?,?,?,?);";
    final String GETALL = "SELECT num_pedido, cantidad, fecha, fk_cliente, fk_articulo FROM pedido";
    final String GETONE = "SELECT num_pedido, cantidad, fecha, fk_cliente, fk_articulo FROM pedido WHERE num_pedido = ?";
    final String UPDATE = "UPDATE pedido SET cantidad=?, fecha=?, fk_cliente=?, fk_articulo=? WHERE num_pedido=?";
    final String DELETE = "DELETE FROM pedido WHERE num_pedido = ?";
    final String SELECT_FECHA = "SELECT fecha FROM pedido WHERE num_pedido = ?";
    final String SELECT_ARTICULO = "SELECT num_pedido, cantidad, fecha, fk_cliente, fk_articulo FROM pedido WHERE num_pedido = ?";
    private Connection conn;
    public MySQLPedidoDAO(Connection conn) {
        this.conn = conn;
    }

    public MySQLPedidoDAO() {
    }

    /**
     * Convierte un objeto LocalDateTime a un objeto Timestamp para la base de datos.
     * @param date Objeto LocalDateTime a convertir.
     * @return Objeto Timestamp con el valor de date.
     */
    public static Timestamp cambiarFechaSQL(LocalDateTime date) {
        Timestamp timestamp = Timestamp.valueOf(date);
        return timestamp;
    }

    /**
     * Convierte un ResultSet en un objeto Pedido.
     * @param rs El ResultSet obtenido de la base de datos.
     * @return Un objeto Pedido.
     * @throws SQLException Si ocurre un error al procesar el ResultSet.
     */
    private Pedido convertir (ResultSet rs) throws SQLException{
        int num_pedido = rs.getInt("num_pedido");
        int cantidad = rs.getInt("cantidad");
        Timestamp fecha = rs.getTimestamp("fecha");
        LocalDateTime f = fecha.toLocalDateTime();
        String cliente = rs.getString("fk_cliente");
        String articulo = rs.getString("fk_articulo");
        Pedido pedido = new Pedido(num_pedido, cantidad, f);

        return pedido;
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     * @param p El objeto Pedido que contiene los datos a insertar.
     * @throws DAOException Sí ocurre un error durante la operación SQL.
     */
    @Override
    public void insertar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit para manejar la transacción
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(INSERT);
            stat.setInt(1, p.getNumPedido());
            stat.setInt(2, p.getCantidad());
            Timestamp fecha_pedido = cambiarFechaSQL(p.getFecha());
            stat.setTimestamp(3, fecha_pedido);
            stat.setString(4, p.getCliente().getEmail());
            stat.setString(5, p.getArticulo().getcodigoArticulo());
            stat.executeUpdate();

            // Confirmar la transacción si no hay errores
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
            // Restaurar el auto-commit y cerrar los recursos
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
    /**
     * Modifica los datos de un pedido en la base de datos.
     * @param p El objeto Pedido con los datos actualizados.
     * @throws DAOException Sí ocurre un error durante la operación SQL.
     */
    @Override
    public void modificar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setInt(1, p.getCantidad());
            Timestamp fecha_pedido = cambiarFechaSQL(p.getFecha());
            stat.setTimestamp(2, fecha_pedido);
            stat.setString(3, p.getCliente().getEmail());
            stat.setString(4, p.getArticulo().getcodigoArticulo());
            stat.setInt(5, p.getNumPedido());
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
    /**
     * Elimina un pedido de la base de datos.
     * @param p El objeto Pedido a eliminar.
     * @throws DAOException Sí ocurre un error durante la operación SQL.
     */
    @Override
    public void eliminar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, p.getNumPedido());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("El pedido no se ha borrado.");
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
    public List<Pedido> obtenerTodos() {
        return null;
    }

    /**
     * Obtiene un pedido por su número de identificación.
     * @param id El ID del pedido.
     * @return El objeto Pedido correspondiente.
     * @throws DAOException Si no se encuentra el pedido o hay un error en la consulta.
     */
    @Override
    public Pedido obtener(Integer id) throws DAOException{
        int idd = id;
        conn = new MySQLDAOManager().conectar();
        PreparedStatement stat = null;
        ResultSet rs = null;
        Pedido p = null;
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setInt(1, idd);
            rs = stat.executeQuery();
            System.out.println("Hace la query");
            if(rs.next()){
                p = convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado ese registro.");
            }

        }catch (SQLException ex){
            throw new DAOException("Error en SQL", ex);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return p;
    }

    /**
     * Obtiene el código del artículo relacionado con un pedido.
     * @param id El ID del pedido.
     * @return El código del artículo.
     * @throws DAOException Si hay un error en la consulta.
     */
    public String obtenerArticulo(Integer id) throws DAOException{
        int idd = id;
        conn = new MySQLDAOManager().conectar();
        PreparedStatement stat = null;
        ResultSet rs = null;
        String a  = null;
        try{
            stat = conn.prepareStatement(SELECT_ARTICULO);
            stat.setInt(1, idd);
            rs = stat.executeQuery();
            System.out.println("Hace la query");
            if(rs.next()){
                a = rs.getString("fk_articulo");
            } else {
                throw new DAOException("No se ha encontrado ese registro.");
            }

        }catch (SQLException ex){
            throw new DAOException("Error en SQL", ex);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return a;
    }

    @Override
    public List<Pedido> obtenerPorCliente(String cliente) throws DAOException {
        return null;
    }
}
