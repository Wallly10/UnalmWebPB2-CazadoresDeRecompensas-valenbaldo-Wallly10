package ar.edu.unlam.pb2.dominio;


public abstract class Cazador {

	protected String nombre;
	protected Integer experiencia;
	

	public Cazador(String nombre, Integer experienciaInicial) {
		this.nombre = nombre;
		this.experiencia = experienciaInicial;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getExperiencia() {
		return experiencia;
	}
	
	public Boolean puedeCapturar(Profugo profugo) {
		return this.experiencia > profugo.getNivelInocencia() && condicionEspecifica(profugo);
	}
	
	protected abstract Boolean condicionEspecifica(Profugo profugo);

	public abstract void intimidar(Profugo profugo); 

	
	
	
}
