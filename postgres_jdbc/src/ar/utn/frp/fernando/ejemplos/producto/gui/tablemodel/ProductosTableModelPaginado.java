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

package ar.utn.frp.fernando.ejemplos.producto.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ar.utn.frp.fernando.ejemplos.producto.dao.IProductoDAO;
import ar.utn.frp.fernando.ejemplos.producto.dao.exception.DAOException;
import ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.util.DAOFactory;
import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.AbstractPaginable;
import ar.utn.frp.fernando.ejemplos.producto.modelo.Producto;


public class ProductosTableModelPaginado extends AbstractPaginable implements TableModel{

	public List<Producto> productos = new ArrayList<Producto>();
	public List<String> nombreColumnas = new ArrayList<String>();
	public List<TableModelListener> tableModelListeners = new ArrayList<TableModelListener>();
	
	public IProductoDAO productoDao = DAOFactory.getDAOFactory(DAOFactory.PGDB).getProductoDAO();
	
	public ProductosTableModelPaginado(){
		nombreColumnas.add("C�igo");
		nombreColumnas.add("Nombre");
		nombreColumnas.add("Disponible");
	}
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		Object obj=getValueAt(0, arg0);
		if(obj!=null)
			return getValueAt(0, arg0).getClass();
		else
			return null;
	}

	@Override
	public int getColumnCount() {
		if(nombreColumnas!=null){
			return nombreColumnas.size();
		}
		return 0;
	}

	@Override
	public String getColumnName(int arg0) {
		if(nombreColumnas!=null && arg0>=0 && nombreColumnas.size()>arg0){
			return nombreColumnas.get(arg0);
		}
		return null;
	}

	@Override
	public int getRowCount() {
		if(productos!=null){
			return productos.size();
		}
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(productos!=null && arg0>=0 && arg1>=0 && productos.size()>arg0){
			Producto producto=productos.get(arg0);
			switch(arg1){
				case 0: return producto.getCodigo();
				case 1: return producto.getNombre();
				case 2: return producto.getDisponible();
			}
		}
		return null;
	}
	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		if(productos!=null && arg1>=0 && arg2>=0 && productos.size()>arg1){
			Producto producto=productos.get(arg1);
			switch(arg2){
				case 0:
					Integer codigoNuevo=null;
					if(arg0!=null){
						codigoNuevo=(Integer)arg0;
					}
					producto.setCodigo(codigoNuevo);
					break;
				case 1: 
					String nombreNuevo=null;
					if(arg0!=null){
						nombreNuevo=(String)arg0;
					}
					producto.setNombre(nombreNuevo);
					break;
				case 2: 
					Boolean disponible=false;
					if(arg0!=null){
						disponible=(Boolean)arg0;
					}
					producto.setDisponible(disponible);
					break;
			}
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		tableModelListeners.add(arg0);
	}
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		tableModelListeners.remove(arg0);
	}

	@Override
	public void actualizar(int indice,int cantidadRenglones) throws DAOException{
		productos=productoDao.findAll(indice, cantidadRenglones);		
	}
	
	@Override
	public int getCantidadRenglones() {
		return productos.size();
	}
	
	public void agregarProducto(Producto producto) throws DAOException,Exception{
		producto.setId(productoDao.create(producto));
	}
	
	public void modificarProducto(Integer indice, Producto productoModificado) throws DAOException,Exception{		
		productoModificado.setId(productoDao.update(productoModificado));
		Producto productoNoModificado=productos.get(indice);
		
		if(productoModificado!=null && productoModificado.getId()!=null){
			productoNoModificado.setId(productoModificado.getId());	
			productoNoModificado.setCodigo(productoModificado.getCodigo());
			productoNoModificado.setDisponible(productoModificado.getDisponible());
		}
	}
	
	public void eliminarProducto(int indice) throws DAOException,Exception{
		Producto producto = productos.get(indice);
		productoDao.remove(producto.getId());	
	}
}
