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

package ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.listener;

import java.util.EventListener;

import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.event.PaginadorEvent;

public interface PaginadorEventListener extends EventListener {
	public void paginadorEventOcurrido(PaginadorEvent paginadorEvent);
}
