package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.ExperienciaNegativaArgumentException;

public class CazadorUrbano extends Cazador {

	public CazadorUrbano(String nombre, Integer experienciaInicial) throws ExperienciaNegativaArgumentException {
		super(nombre, experienciaInicial);
	}
	
	 @Override
	public Boolean condicionEspecifica(Profugo profugo) {
	        return !profugo.esNervioso();
	    }

	    @Override
	    public void intimidar(Profugo profugo) {
	        profugo.calmarse();
	    }
	
}