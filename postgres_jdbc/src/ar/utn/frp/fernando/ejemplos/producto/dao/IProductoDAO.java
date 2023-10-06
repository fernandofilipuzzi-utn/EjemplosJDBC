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

package ar.utn.frp.fernando.ejemplos.producto.dao;

import java.util.List;

import ar.utn.frp.fernando.ejemplos.producto.dao.exception.DAOException;
import ar.utn.frp.fernando.ejemplos.producto.modelo.Producto;

public interface IProductoDAO {
	public Integer create(Producto producto) throws DAOException;
	public Integer update(Producto producto) throws DAOException;
	public Integer remove(Integer id) throws DAOException;
	public Producto find(Integer id) throws DAOException;	
	public List<Producto> findAll() throws DAOException;
	public List<Producto> findAll(int indice,int cantidadRenglones) throws DAOException;
}
