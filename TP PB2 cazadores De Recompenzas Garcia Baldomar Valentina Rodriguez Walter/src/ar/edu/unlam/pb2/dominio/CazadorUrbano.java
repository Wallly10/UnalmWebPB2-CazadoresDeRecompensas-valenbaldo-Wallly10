package ar.edu.unlam.pb2.dominio;

public class CazadorUrbano extends Cazador {

	public CazadorUrbano(String nombre, Integer experienciaInicial) {
		super(nombre, experienciaInicial);
	}
	
	 @Override
	    protected Boolean condicionEspecifica(Profugo profugo) {
	        return !profugo.esNervioso();
	    }

	    @Override
	    public void intimidar(Profugo profugo) {
	        profugo.calmarse();
	    
	    }
	
}