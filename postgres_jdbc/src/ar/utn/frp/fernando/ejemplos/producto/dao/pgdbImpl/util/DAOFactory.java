package ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.util;

import ar.utn.frp.fernando.ejemplos.producto.dao.IProductoDAO;

public abstract class DAOFactory {
	//lista de daos soportados por el factory
	public static final int PGDB = 1;
	
	//lista de metodos para cada dao que puede ser creado.
	//los factories deberan implementar estos metodos.
	public abstract IProductoDAO getProductoDAO();
	
	public static DAOFactory getDAOFactory(int witchFactory) {
		try {
			switch(witchFactory){
			case PGDB:
				return new PgdbDAOFactory();
			default:
				return null;
			}
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't create DAOFactory: ");
		}
	}
	
}
