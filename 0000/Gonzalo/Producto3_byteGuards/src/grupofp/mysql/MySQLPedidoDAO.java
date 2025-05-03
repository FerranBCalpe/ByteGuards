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
    public static Timestamp cambiarFechaSQL(LocalDateTime date) {
        Timestamp timestamp = Timestamp.valueOf(date);
        return timestamp;
    }

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

    @Override
    public void insertar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(INSERT);
            stat.setInt(1, p.getNumPedido());
            stat.setInt(2, p.getCantidad());
            Timestamp fecha_pedido = cambiarFechaSQL(p.getFecha());
            stat.setTimestamp(3, fecha_pedido);
            stat.setString(4, p.getCliente().getEmail());
            stat.setString(5, p.getArticulo().getcodigoArticulo());
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
    public void modificar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(UPDATE);
            stat.setInt(1, p.getCantidad());
            Timestamp fecha_pedido = cambiarFechaSQL(p.getFecha());
            stat.setTimestamp(2, fecha_pedido);
            stat.setString(3, p.getCliente().getEmail());
            stat.setString(4, p.getArticulo().getcodigoArticulo());
            stat.setInt(5, p.getNumPedido());
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
    public void eliminar(Pedido p) throws DAOException {
        PreparedStatement stat = null;
        Connection conn = null;

        try {
            conn = new MySQLDAOManager().conectar();

            // Deshabilitar auto-commit
            conn.setAutoCommit(false);

            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, p.getNumPedido());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("El pedido no se ha borrado.");
            }

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
    public List<Pedido> obtenerTodos() {
        return null;
    }

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
