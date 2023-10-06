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

package ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.utn.frp.fernando.ejemplos.producto.dao.IProductoDAO;
import ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.exception.PgdbProductoDAOException;
import ar.utn.frp.fernando.ejemplos.producto.modelo.Producto;

public class PgdbProductoDAO implements IProductoDAO {
	private static final String INSERT_SQL =" insert into public.productos (codigo, nombre, disponible) values (?,?,?)";
	private static final String UPDATE_SQL =" update public.productos set codigo=?, nombre=?, disponible=? where id=?";
	private static final String DELETE_SQL =" delete from public.productos where id=?";
	private static final String FIND_SQL =  " select o.id, o.codigo, o.nombre, o.disponible "
										   +" from public.productos as o ";
	private static final String FIND_ALL_SQL =  " select id,codigo,nombre,disponible "
											   +" from public.productos ";
	private static final String FIND_ALL_SQL_PAGINADO =  " select id,codigo,nombre,disponible "
		   									  			+" from public.productos o "
		   									  			+" order by o.codigo "
		   									  			+" limit ?1 offset ?2 ";
	
	public static final String bdUrl = "jdbc:postgresql://localhost/productos?user=postgres&password=123456";
	public static final String url= "jdbc:postgresql://localhost/productos";	
	
	public PgdbProductoDAO(){
		super();
	}

	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(bdUrl);
		return connection;
	}
	
	
	@Override
	public Integer create(Producto producto) throws PgdbProductoDAOException{
		if(producto!=null){
		    Connection conn = null;
		    PreparedStatement sth = null;
			ResultSet rs =null;
		    try {
		        conn = getConnection();
		        //Statement.NO_GENERATED_KEYS por el id esta definido con serial.
		        sth = conn.prepareStatement(INSERT_SQL,
		        							ResultSet.TYPE_SCROLL_INSENSITIVE,
		        							Statement.NO_GENERATED_KEYS);
		        
		        sth.clearParameters();
		        sth.setInt(1, producto.getCodigo());
		        sth.setString(2, producto.getNombre());
		        sth.setBoolean(3, producto.getDisponible());
		        
		        sth.executeUpdate();
		        
		        rs=sth.getGeneratedKeys();
		        if(rs.next()){
		        	Integer id = (Integer) rs.getInt(1);
			        producto.setId(id);
		        }
		    }catch (Exception e) {
		    	 throw new PgdbProductoDAOException(PgdbProductoDAOException.CREATE, 
		    			 							INSERT_SQL,e);
		    } finally {
		    	try{
		    		if(!conn.isClosed()) conn.close();
		    	}catch(Exception e){
		    		throw new PgdbProductoDAOException(PgdbProductoDAOException.CREATE, 
		    											INSERT_SQL,e);
		    	}
			}
		    return producto.getId();
	    }
		return null;
	}

	@Override
	public Integer update(Producto producto) throws PgdbProductoDAOException{
	    Connection conn = null;
	    PreparedStatement sth = null;
	    Integer idUpdate=null;
	    ResultSet rs=null;
	    try {
	        conn = getConnection();
	        
	        sth = conn.prepareStatement(UPDATE_SQL);	
	        
	        sth.clearParameters();
	        sth.setInt(1, producto.getCodigo());
	        sth.setString(2, producto.getNombre());
	        sth.setBoolean(3, producto.getDisponible());
	        
	        //id del producto a updatear
	        sth.setInt(4, producto.getId());
	        
	        sth.executeUpdate();
	        rs=sth.getGeneratedKeys();
	        if(rs.next()){
	        	idUpdate = (Integer) rs.getInt(1);
		        producto.setId(idUpdate);
	        }
	    }catch (Exception e) {
	    	 throw new PgdbProductoDAOException(PgdbProductoDAOException.UPDATE, 
	    			 							UPDATE_SQL,e);
	    } finally {
	    	try{
	    		if(!conn.isClosed()) conn.close();
	    	}catch(Exception e){
	    		throw new PgdbProductoDAOException(PgdbProductoDAOException.UPDATE, 
	    											UPDATE_SQL,e);
	    	}
		}
	    return idUpdate;
	}
	
	@Override
	public Integer remove(Integer id) throws PgdbProductoDAOException{
	    Connection conn = null;
	    PreparedStatement sth = null;
	    Integer idUpdate=null;
	    ResultSet rs=null;
	    try {
	    	conn = getConnection();
	        
	    	sth = conn.prepareStatement(DELETE_SQL);	        
	        
	    	sth.clearParameters();
	    	sth.setInt(1, id);
	    	
	        sth.executeUpdate();
	        rs=sth.getGeneratedKeys();
	        if(rs.next()){
		        idUpdate = (Integer) rs.getInt(1);
	        }
	    }catch (Exception e) {
	    	throw new PgdbProductoDAOException(PgdbProductoDAOException.REMOVE, 
	    										DELETE_SQL,e);
	    } finally {
	    	try{
	    		if(!conn.isClosed()) conn.close();
	    	}catch(Exception e){
	    		throw new PgdbProductoDAOException(PgdbProductoDAOException.REMOVE, 
	    											DELETE_SQL,e);
	    	}
		}
	    return idUpdate;
	}
	
	@Override
	public Producto find(Integer id) throws PgdbProductoDAOException{
		Producto producto=null;
		Connection conn = null;
		Statement sth = null;
		try {
			conn = getConnection();
			String sql=FIND_SQL.replace("?", id.toString());
			sth = conn.createStatement();
			ResultSet rs = sth.executeQuery(sql);
		    rs.next();
		    
		    producto = new Producto();
		    int idActual=rs.getInt("id");
		    if(idActual>0)
		    	producto.setId(idActual);
		    int codigo=rs.getInt("codigo");
		    if(codigo>0)
		    	producto.setCodigo(codigo);
	    	producto.setNombre(rs.getString("nombre"));
	    	producto.setDisponible(rs.getBoolean("disponible"));
		    
		}catch (SQLException e) {
			throw new PgdbProductoDAOException(PgdbProductoDAOException.FIND, 
												FIND_ALL_SQL,e);
	    } finally {
	    	try{
	    		if(!conn.isClosed()) conn.close();
	    	}catch(Exception e){
	    		throw new PgdbProductoDAOException(PgdbProductoDAOException.FIND, 
	    											FIND_ALL_SQL,e);
	    	}
		}
		
		return producto;
	}
	
	@Override
	public List<Producto> findAll() throws PgdbProductoDAOException{
		List<Producto> productos = new ArrayList<Producto>();
				
		Connection conn = null;
		Statement sth=null;
		ResultSet rs =null;
		try {
			conn = getConnection();
			sth = conn.createStatement();
		    rs = sth.executeQuery(FIND_ALL_SQL);
		    		    
		    while(rs.next()) {
		    	Producto producto = new Producto();
				int idActual=rs.getInt("id");
				if(idActual>0)
					producto.setId(idActual);
				int codigo=rs.getInt("codigo");
				if(codigo>0)
					producto.setCodigo(codigo);
				producto.setNombre(rs.getString("nombre"));
				producto.setDisponible(rs.getBoolean("disponible"));
		    	
		    	productos.add(producto);
		    }
		}catch (SQLException e) {
			throw new PgdbProductoDAOException(PgdbProductoDAOException.FINDALL_PAGINADO,FIND_ALL_SQL_PAGINADO,e);
	    } finally {
	    	try{
	    		if(!conn.isClosed()) conn.close();
	    	}catch(Exception e){
	    		throw new PgdbProductoDAOException(PgdbProductoDAOException.FINDALL_PAGINADO, FIND_ALL_SQL_PAGINADO,e);
	    	}
		}
	    return productos;
	}
	

	@Override
	public List<Producto> findAll(int indice,int cantidadRenglones) throws PgdbProductoDAOException{
		List<Producto> productos = new ArrayList<Producto>();
				
		Connection conn = null;
		Statement sth=null;
		ResultSet rs=null;
		try {
			conn = getConnection();	
			//http://stackoverflow.com/questions/4259529/scrollable-resultset-jdbc-postgresql
			String sql="";
			sql=sql.replace("", FIND_ALL_SQL_PAGINADO);
			sql=sql.replace("?1", Integer.toString(cantidadRenglones));
			sql=sql.replace("?2", Integer.toString(indice));
			sth = conn.createStatement();//ResultSet.TYPE_SCROLL_INSENSITIVE,Statement.NO_GENERATED_KEYS);
			//sth.setInt(1, cantidadRenglones);  
		    //sth.setInt(2, indice);  
		    rs = sth.executeQuery(sql);
		    while(rs.next()) {
		    	Producto producto = new Producto();
				int idActual=rs.getInt("id");
				if(idActual>0)
					producto.setId(idActual);
				int codigo=rs.getInt("codigo");
				if(codigo>0)
					producto.setCodigo(codigo);
				producto.setNombre(rs.getString("nombre"));
				producto.setDisponible(rs.getBoolean("disponible"));
		    	//http://es.wikipedia.org/wiki/SQLJ
		    	productos.add(producto);
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try{
	    		if(!conn.isClosed()) conn.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
	    return productos;
	}
	
}

/*
 * org.postgresql.util.PSQLException: This ResultSet is closed.
	at org.postgresql.jdbc2.AbstractJdbc2ResultSet.checkClosed(AbstractJdbc2ResultSet.java:2674)
	at org.postgresql.jdbc2.AbstractJdbc2ResultSet.next(AbstractJdbc2ResultSet.java:1806)
	at pgdao.PgdbProductoDAO.findAll(PgdbProductoDAO.java:225)
	at tablemodel.ProductosTableModel.actualizar(ProductosTableModel.java:129)
	at custom.componente.Paginador.actionPerformed(Paginador.java:126)
	at javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1995)
	at javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2318)
	at javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:387)
	at javax.swing.DefaultButtonModel.setPressed(DefaultButtonModel.java:242)
	at javax.swing.plaf.basic.BasicButtonListener.mouseReleased(BasicButtonListener.java:236)
	at java.awt.Component.processMouseEvent(Component.java:6263)
	at javax.swing.JComponent.processMouseEvent(JComponent.java:3255)
	at java.awt.Component.processEvent(Component.java:6028)
	at java.awt.Container.processEvent(Container.java:2041)
	at java.awt.Component.dispatchEventImpl(Component.java:4630)
	at java.awt.Container.dispatchEventImpl(Container.java:2099)
	at java.awt.Component.dispatchEvent(Component.java:4460)
	at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4574)
	at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4238)
	at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4168)
	at java.awt.Container.dispatchEventImpl(Container.java:2085)
	at java.awt.Window.dispatchEventImpl(Window.java:2475)
	at java.awt.Component.dispatchEvent(Component.java:4460)
	at java.awt.EventQueue.dispatchEvent(EventQueue.java:599)
	at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:269)
	at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:184)
	at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:174)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:169)
	at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:161)
	at java.awt.EventDispatchThread.run(EventDispatchThread.java:122)
 * */
