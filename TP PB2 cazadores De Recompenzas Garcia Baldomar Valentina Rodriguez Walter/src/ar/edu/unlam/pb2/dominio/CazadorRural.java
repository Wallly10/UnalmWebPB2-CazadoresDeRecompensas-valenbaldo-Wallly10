package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.ExperienciaNegativaArgumentException;

public class CazadorRural extends Cazador{

	public CazadorRural(String nombre, Integer experienciaInicial) throws ExperienciaNegativaArgumentException { 
		super(nombre, experienciaInicial); 
		} 
	
	@Override
    public void intimidar(Profugo profugo) {
        profugo.volverNervioso();;
    
    }

	@Override
	public Boolean condicionEspecifica(Profugo profugo) {
		 return profugo.esNervioso();
	}

	
}
