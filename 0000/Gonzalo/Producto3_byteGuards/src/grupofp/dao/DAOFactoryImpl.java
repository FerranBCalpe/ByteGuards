package grupofp.dao;

import grupofp.mysql.*;

public class DAOFactoryImpl extends DAOFactory {
    @Override
    public ArticuloDAO getArticuloDAO() {
        return new MySQLArticuloDAO();
    }
    public ClienteDAO getClienteDAO(){return new MySQLClienteDAO();
    }

    @Override
    public ClienteEstandarDAO getClienteEstandarDAO() {
        return new MySQLClienteEstandarDAO();
    }

    @Override
    public ClientePremiumDAO getClientePremiumDAO() {
        return new MySQLClientePremiumDAO();
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return new MySQLPedidoDAO();
    }
}
