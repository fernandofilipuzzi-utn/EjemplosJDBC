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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ar.utn.frp.fernando.ejemplos.producto.dao.exception.DAOException;
import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.Paginador;
import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.event.PaginadorEvent;
import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.listener.PaginadorEventListener;
import ar.utn.frp.fernando.ejemplos.producto.gui.tablemodel.ProductosTableModelPaginado;
import ar.utn.frp.fernando.ejemplos.producto.modelo.Producto;

public class App extends JFrame implements ActionListener, ListSelectionListener, TableModelListener, PaginadorEventListener{
	private static final long serialVersionUID = 1L;
	
	protected static final String CERRAR="CERRAR";
	protected static final String NUEVO="NUEVO";
	protected static final String AGREGAR="AGREGAR";
	protected static final String MODIFICAR="MODIFICAR";
	protected static final String ELIMINAR="ELIMINAR";
	
	protected JTextField jtfProductoCodigo=new JTextField(15);
	protected JCheckBox jchbProductoDisponible=new JCheckBox();
	protected JTextField jtfProductoNombre=new JTextField(15);
	
	protected JButton jbNuevo=new JButton("Nuevo");
	protected JButton jbAgregarModificar=new JButton("AgregarModificar");
	protected JButton jbEliminar=new JButton("Eliminar");
	
	protected Producto producto=new Producto();
	protected ProductosTableModelPaginado tmpProductos=new ProductosTableModelPaginado();
	protected Paginador cPaginadorSuperior;
	protected Paginador cPaginadorInferior;
			
	protected JScrollPane jsp=new JScrollPane();
	protected JTable jtTabla = new JTable();	
	protected JButton jbCerrar = new JButton("Cerrar");
	
	
	public App(){
		this.setTitle("Ejemplo 1 DAO: JDBC");
		this.setLayout(new BorderLayout());
		
		JPanel jpDatos = new JPanel();
		jpDatos.setLayout(new BorderLayout());
		this.add(jpDatos,BorderLayout.PAGE_START);
		jpDatos.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
		
		/*formulario*/
		JPanel jpFormulario = new JPanel();
		jpFormulario.setLayout(new GridBagLayout());
		jpDatos.add(jpFormulario,BorderLayout.CENTER);		
		
		jpFormulario.add(new JLabel("Código: ",SwingConstants.RIGHT),new GridBagConstraints(0,0,1,1,0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,5,10,5),1,1));
		jpFormulario.add(jtfProductoCodigo,new GridBagConstraints(1,0,1,1,0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,10,5),1,1));
		jpFormulario.add(jchbProductoDisponible,new GridBagConstraints(2,0,1,1,0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,10,5),1,1));		
		jpFormulario.add(new JLabel("Nombre: ",SwingConstants.RIGHT),new GridBagConstraints(0,1,1,1,0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,5,10,5),1,1));
		jpFormulario.add(jtfProductoNombre,new GridBagConstraints(1,1,2,1,0.0,0.0, GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,5,10,5),1,1));
		
		JPanel jpBarraBotonesFormulario = new JPanel();
		jpBarraBotonesFormulario.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		jpDatos.add(jpBarraBotonesFormulario,BorderLayout.SOUTH);
		
		jpBarraBotonesFormulario.add(jbNuevo);
		jbNuevo.addActionListener(this);
		jbNuevo.setVisible(false);
		jpBarraBotonesFormulario.add(jbAgregarModificar);
		jbAgregarModificar.addActionListener(this);
		jpBarraBotonesFormulario.add(jbEliminar);
		jbEliminar.addActionListener(this);
		
		/*fin datos*/
		
		/*detalle*/
		
		JPanel jpDetalle = new JPanel();
		this.add(jpDetalle,BorderLayout.CENTER);
		jpDetalle.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
		jpDetalle.setLayout(new BorderLayout());
		
		cPaginadorSuperior=new Paginador(tmpProductos);		
		jpDetalle.add(cPaginadorSuperior,BorderLayout.PAGE_START);
		cPaginadorSuperior.addPaginadorEventListener(this);//llama al actualizar tabla, redibuja la tabla
		cPaginadorSuperior.inicio(); //hace la consulta
				
		jpDetalle.add(jsp,BorderLayout.CENTER);		
		jtTabla.setModel(tmpProductos);
		jtTabla.setAutoCreateRowSorter(true); 
		jtTabla.getSelectionModel().addListSelectionListener(this);		
		jtTabla.setPreferredScrollableViewportSize(new Dimension(500, 85));
		jtTabla.setFillsViewportHeight(true);		
		jsp.setViewportView(jtTabla);
				
		/*
		cPaginadorInferior=new Paginador(tmpProductos);
		jpDetalle.add(cPaginadorInferior,BorderLayout.PAGE_END);
		*/
		
		/*fin de detalle*/
		
		JPanel jpBarraBotonesInferior = new JPanel();
		jpBarraBotonesInferior.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.add(jpBarraBotonesInferior, BorderLayout.PAGE_END);
				
		jpBarraBotonesInferior.add(jbCerrar);
		jbCerrar.setActionCommand(App.CERRAR);
		jbCerrar.addActionListener(this);
		
		nuevoProducto();
		
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e!=null && App.CERRAR.equals(e.getActionCommand())){
			System.exit(0);
		}else if(e!=null && App.NUEVO.equals(e.getActionCommand())){
			nuevoProducto();
			actualizarTabla();
		}else if(e!=null && App.AGREGAR.equals(e.getActionCommand())){
			addProducto();
			nuevoProducto();
			actualizarTabla();
		}else if(e!=null && App.MODIFICAR.equals(e.getActionCommand())){
			modificarProducto();
			nuevoProducto();
			actualizarTabla();
		}else if(e!=null && App.ELIMINAR.equals(e.getActionCommand())){
			eliminarProducto();
			nuevoProducto();
			actualizarTabla();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int viewRow = jtTabla.getSelectedRow();
        if (viewRow < 0) {
            System.out.println("");
        } else {
            int modelRow = jtTabla.convertRowIndexToModel(viewRow);
            System.out.println(String.format("Selected Row in view: %d. " +"Selected Row in model: %d.",viewRow, modelRow));
            seleccionarProducto();
        }
	}

	@Override
	public void tableChanged(TableModelEvent event) {
		int row = event.getFirstRow();
		int column = event.getColumn();
		ProductosTableModelPaginado model = (ProductosTableModelPaginado) event.getSource();
		String columnName =  model.getColumnName(column);
		Producto data = (Producto)model.getValueAt(row, column);
		System.out.println(data.getId()+"-"+data.getCodigo()+"-"+data.getNombre()+"-"+columnName);		
	}
	
	@Override
	public void paginadorEventOcurrido(PaginadorEvent paginadorEvent) {
		actualizarTabla();
	}	
	
	public void seleccionarProducto(){
		this.producto=tmpProductos.productos.get(jtTabla.getSelectedRow());
		if(producto!=null){
			jtfProductoCodigo.setText(producto.getCodigo().toString());
			jtfProductoNombre.setText(producto.getNombre());
			jchbProductoDisponible.setSelected(producto.getDisponible());
		}
		
		if(this.producto!=null){
			jbNuevo.setVisible(true);
			jbNuevo.setActionCommand(NUEVO);;
			jbAgregarModificar.setText("Modificar");
			jbAgregarModificar.setActionCommand(MODIFICAR);
			jbEliminar.setText("Eliminar");
			jbEliminar.setActionCommand(ELIMINAR);
		}else{
			nuevoProducto();
		}
	}
	public void nuevoProducto(){
		producto=new Producto();
		jtfProductoCodigo.setText("");
		jtfProductoNombre.setText("");
		
		jbNuevo.setVisible(false);
		jbAgregarModificar.setText("Agregar");
		jbAgregarModificar.setActionCommand(App.AGREGAR);
		jbEliminar.setText("Limpiar");
		jbEliminar.setActionCommand(App.NUEVO);
	}
	public void addProducto(){	
		producto = new Producto();			
		Integer codigo=null;
		if(jtfProductoCodigo.getText()!=null 
					&& !jtfProductoCodigo.getText().equals("")
					&& !jtfProductoCodigo.getText().contains(" ")){
			codigo=Integer.parseInt(jtfProductoCodigo.getText());
		}
		producto.setCodigo(codigo);			
		producto.setNombre(jtfProductoNombre.getText());		
		producto.setDisponible(jchbProductoDisponible.isSelected());
		
		try{
			tmpProductos.agregarProducto(producto);
		}catch(DAOException e){
			JOptionPane.showMessageDialog(this, e.mensaje);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
		
		/*limpia el formulario*/
		producto=new Producto();
		jtfProductoCodigo.setText("");
		jtfProductoNombre.setText("");
		jchbProductoDisponible.setSelected(false);
		
		jbAgregarModificar.setText("Agregar");
		jbAgregarModificar.setActionCommand(App.AGREGAR);
		jbEliminar.setText("Eliminar");		
		jbEliminar.setActionCommand(App.ELIMINAR);
		
		cPaginadorSuperior.inicio();
	}
	
	public void modificarProducto(){
		if(producto!=null){
			Integer codigo=null;
			if(jtfProductoCodigo.getText()!=null){
				codigo=Integer.parseInt(jtfProductoCodigo.getText());
			}
			producto.setCodigo(codigo);
			producto.setNombre(jtfProductoNombre.getText());
			producto.setDisponible(jchbProductoDisponible.isSelected());
		}		
		try{
			tmpProductos.modificarProducto(jtTabla.getSelectedRow(), producto);
			cPaginadorSuperior.inicio();
		}catch(DAOException e){
			JOptionPane.showMessageDialog(this, e.mensaje);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	public void eliminarProducto(){
		try{
			tmpProductos.eliminarProducto(jtTabla.getSelectedRow());
		}catch(DAOException e){
			JOptionPane.showMessageDialog(this, e.mensaje);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		this.jtTabla.repaint();
		cPaginadorSuperior.inicio();
	}
	
	public void actualizarTabla(){				
		jsp.remove(jtTabla);
		jtTabla.setModel(tmpProductos);		
		jtTabla.repaint();
		jsp.setViewportView(jtTabla);		
		jsp.revalidate();
	}
	
	public static void main(String [] args){
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	App app = new App();
	        		app.setVisible(true);
	            }
	     });	
	}
}
