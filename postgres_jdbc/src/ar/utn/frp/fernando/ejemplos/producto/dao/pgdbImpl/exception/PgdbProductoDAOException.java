/*
 * @autor:
 * 		fernando rafael filipuzzi
 * 
 * @e-mail:
 * 		fernando6867@gmail.com
 * 
 * @sitio web:
 * 		http://hdcm.sytes.net
 * 		http://hdcm.com.ar
 * */

package ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.exception;

import ar.utn.frp.fernando.ejemplos.producto.dao.exception.DAOException;

public class PgdbProductoDAOException extends DAOException {
	private static final long serialVersionUID = 1L;

	public PgdbProductoDAOException(String tipo, String sentenciaSQL,Exception e) {
		super(tipo, sentenciaSQL, e);
		System.out.println("-"+tipo);
		System.out.println("-"+sentenciaSQL);
		e.printStackTrace();
		System.out.println("-");
	}

}
