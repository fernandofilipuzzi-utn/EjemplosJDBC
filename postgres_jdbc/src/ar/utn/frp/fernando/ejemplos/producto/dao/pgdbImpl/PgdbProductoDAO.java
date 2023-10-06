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
import ar.utn.frp.fernando.ejemplos.producto.dao.pgdbImpl.util.PgdbConfig;
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
	
	
	
	public PgdbProductoDAO(){
		super();
	}

	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(PgdbConfig.bdUrl);
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
