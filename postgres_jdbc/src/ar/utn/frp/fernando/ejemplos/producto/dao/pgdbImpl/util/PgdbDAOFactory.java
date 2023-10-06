package ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.util;

import ar.utn.frp.fernando.ejemplos.producto.dao.IProductoDAO;
import ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.PgdbProductoDAO;

public class PgdbDAOFactory extends DAOFactory{
	
	public IProductoDAO getProductoDAO(){
		return new PgdbProductoDAO();
	}	
}
