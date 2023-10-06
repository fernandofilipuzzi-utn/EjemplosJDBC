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

package ar.utn.frp.fernando.ejemplos.producto.dao.exception;

public class DAOException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public static final String CREATE="Error al crear el Registro.";
	public static final String UPDATE="Error al modificar el Registro.";
	public static final String REMOVE="Error al eliminar el Registro.";
	public static final String FIND="Error, registro inexistente.";
	public static final String FINDALL="Error al buscar todos los registros";
	public static final String FINDALL_PAGINADO="Error al buscar todos los registros";
				
	public String mensaje;
	public String detalle;
	public String sentenciaSQL;
	
	public DAOException(String tipo, String sentenciaSQL,Exception e){
		this.mensaje=tipo;
		this.detalle=e.getMessage();
		this.sentenciaSQL=sentenciaSQL;
	}
}
