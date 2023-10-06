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


package ar.utn.frp.fernando.ejemplos.producto.gui.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PgdbCreateDb {

	public static final String bdUrl = "jdbc:postgresql://localhost/productos?user=postgres&password=123456";
	public static final String url= "jdbc:postgresql://localhost/productos";
	
	public static void main(String[] args) {
		System.out.println("Creando la tablas productos");
		
		String string =  " create table productos ("
												+"   id serial "
												+" , codigo integer unique "
												+" , nombre varchar(52) "
												+" , disponible boolean "
						+" )";
					
		System.out.println(string);
		Connection conn = null;
		Statement sth=null;
	    try {
	        conn = DriverManager.getConnection(bdUrl);
	        sth = conn.createStatement();
	        sth.execute(string);
	        //sth.executeQuery(string);
	        //conn.commit();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	try{
	    		conn.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }	        
	}
}
