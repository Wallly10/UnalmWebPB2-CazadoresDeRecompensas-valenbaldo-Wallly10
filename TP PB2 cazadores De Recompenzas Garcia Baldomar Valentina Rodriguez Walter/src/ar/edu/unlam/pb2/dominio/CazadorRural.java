package ar.edu.unlam.pb2.dominio;

public class CazadorRural extends Cazador{

	public CazadorRural(String nombre, Integer experienciaInicial) { 
		super(nombre, experienciaInicial); 
		} 
	
	@Override
    public void intimidar(Profugo profugo) {
        profugo.volverNervioso();;
    
    }

	@Override
	protected Boolean condicionEspecifica(Profugo profugo) {
		 return profugo.esNervioso();
	}

	
}
