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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.event.PaginadorEvent;
import ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils.listener.PaginadorEventListener;

public class Paginador extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	protected javax.swing.event.EventListenerList listenerList =new javax.swing.event.EventListenerList();
	
	private static final String INICIO="INICIO";
	private static final String ANTERIOR="ANTERIOR";
	private static final String SIGUIENTE="SIGUIENTE";
	
	
	private JPanel jpBotonesPaginas = new JPanel();
	private JButton jbInicio = new JButton("|<");
	private JButton jbAnterior = new JButton("<");
	private JButton jbSiguiente = new JButton(">");
		
	private Paginable paginable;
	
	public Paginador(Paginable paginable){
		this.setLayout(new FlowLayout());
		
		this.add(jbInicio);
		jbInicio.setActionCommand(INICIO);
		jbInicio.addActionListener(this);
		
		this.add(jbAnterior);
		jbAnterior.setActionCommand(ANTERIOR);
		jbAnterior.addActionListener(this);
		
		jpBotonesPaginas.setLayout(new FlowLayout());				
		
		this.add(jbSiguiente);
		jbSiguiente.setActionCommand(SIGUIENTE);
		jbSiguiente.addActionListener(this);
		
		this.paginable=paginable;
		
		inicio();
	}
	
	public void inicio(){
		jbInicio.setEnabled(false);
		jbAnterior.setEnabled(false);
		jbSiguiente.setEnabled(true);
		
		try{
			paginable.inicio();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	protected void anterior() throws Exception{			
		if(paginable.anterior()){
			jbInicio.setEnabled(true);
			jbAnterior.setEnabled(true);
			jbSiguiente.setEnabled(true);
		}else{
			jbInicio.setEnabled(false);
			jbAnterior.setEnabled(false);
			jbSiguiente.setEnabled(true);
		}
	}
	
	protected void siguiente() throws Exception{
		if(paginable.siguiente()){
			jbInicio.setEnabled(true);
			jbAnterior.setEnabled(true);
			jbSiguiente.setEnabled(true);			
		}else {
			jbSiguiente.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try{			
			if(event!=null && INICIO.equals(event.getActionCommand())){			
				inicio();
			}else if(event!=null && ANTERIOR.equals(event.getActionCommand())){			
				anterior();
			}else if(event!=null && SIGUIENTE.equals(event.getActionCommand())){
				siguiente();
			}		
			
			PaginadorEvent paginadorEvent= new PaginadorEvent(this);
			this.firePaginadorEvent(paginadorEvent);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
    public void addPaginadorEventListener(PaginadorEventListener listener) {
        listenerList.add(PaginadorEventListener.class, listener);
    }

    public void removePaginadorEventListener(PaginadorEventListener listener) {
        listenerList.remove(PaginadorEventListener.class, listener);
    }
    
    void firePaginadorEvent(PaginadorEvent paginadorEvent) {
        Object[] listeners = listenerList.getListenerList();
        
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==PaginadorEventListener.class) {
                ((PaginadorEventListener)listeners[i+1]).paginadorEventOcurrido(paginadorEvent);
            }
        }
    }
}
