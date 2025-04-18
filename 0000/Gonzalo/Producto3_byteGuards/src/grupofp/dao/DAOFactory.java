package grupofp.dao;

public abstract class DAOFactory {
    public abstract ArticuloDAO getArticuloDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract ClienteEstandarDAO getClienteEstandarDAO();
    public abstract ClientePremiumDAO getClientePremiumDAO();
    public abstract PedidoDAO getPedidoDAO();

    public static DAOFactory getDAOFactory(){
        return new DAOFactoryImpl();
    }
}
