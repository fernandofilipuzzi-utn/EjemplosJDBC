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

package ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils;

public interface Paginable {
	public int getCantidadRenglones();
	public void actualizar(int indice,int cantidadRenglones) throws Exception;
	
	public int getIndiceRenglon();
	public void setIndiceRenglon(int indiceRenglon);
	
	public int getMaxCantidadRenglones();
	public void setMaxCantidadRenglones(int maxCantidadRenglones);
	
	
	public void inicio() throws Exception;
	public boolean anterior() throws Exception;
	public boolean siguiente() throws Exception;
}
