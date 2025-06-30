package ar.edu.unlam.pb2.dominio;

public class CazadorSigiloso extends Cazador{

	
	
	public CazadorSigiloso(String nombre, int experienciaInicial) { 
		super(nombre, experienciaInicial); 
		}

	@Override
	protected Boolean condicionEspecifica(Profugo profugo) {
		return profugo.getHabilidad()>50;
	}

	@Override
	public void intimidar(Profugo profugo) {
		profugo.reducirHabilidad(5);
	} 
	
	
	
}
