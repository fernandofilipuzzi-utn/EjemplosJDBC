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

package ar.utn.frp.fernando.ejemplos.producto.modelo;

public class Producto {
	private Integer id;
	private Integer codigo;
	private String nombre;
	private Boolean disponible=false;
	
	public Producto(){		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Boolean getDisponible() {
		if(disponible!=null)
			return disponible;
		else 
			return false;
	}
	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}
}
