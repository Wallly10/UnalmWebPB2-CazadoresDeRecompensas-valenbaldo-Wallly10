package ar.edu.unlam.pb2.dominio;

public abstract class Cazador {

	protected String nombre;
	protected int experiencia;
	

	public Cazador(String nombre, int experienciaInicial) {
		this.nombre = nombre;
		this.experiencia = experienciaInicial;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getExperiencia() {
		return experiencia;
	}
	
	public abstract boolean puedeCapturar(Profugo p); 

	public abstract void intimidar(Profugo p); 

}
