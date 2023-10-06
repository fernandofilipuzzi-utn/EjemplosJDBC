package ar.utn.frp.fernando.ejemplos.producto.gui.swingUtils;

public abstract class  AbstractPaginable implements Paginable{
	private int indiceRenglon=0;
	private int maxCantidadRenglones=5;
	
	@Override
	public int getIndiceRenglon() {
		return indiceRenglon;
	}
	@Override
	public void setIndiceRenglon(int indiceRenglon) {	
		this.indiceRenglon=indiceRenglon;		
	}
	
	@Override
	public int getMaxCantidadRenglones() {
		return maxCantidadRenglones;
	}
	@Override
	public void setMaxCantidadRenglones(int maxCantidadRenglones) {
		this.maxCantidadRenglones=maxCantidadRenglones;		
	}
	
	@Override
	public void inicio() throws Exception {
		indiceRenglon=0;
		actualizar(indiceRenglon,maxCantidadRenglones);		
	}
	
	@Override
	public boolean anterior() throws Exception {
		indiceRenglon=indiceRenglon-maxCantidadRenglones;
		boolean tieneAnterior=indiceRenglon>=maxCantidadRenglones;
		if(!tieneAnterior){
			indiceRenglon=0;			
		}
		actualizar(indiceRenglon,maxCantidadRenglones);
		return tieneAnterior;
	}

	@Override
	public boolean siguiente() throws Exception {
		boolean tieneSiguiente=!(getCantidadRenglones() < maxCantidadRenglones);
		if(tieneSiguiente){			
			indiceRenglon=indiceRenglon+getCantidadRenglones();
			actualizar(indiceRenglon,maxCantidadRenglones);
		}
		return tieneSiguiente;
	}
}
