package ar.edu.unlam.pb2.dominio;

public class Profugo {

	private String nombre;
	private int nivelInocencia;
	private boolean nervioso;

	public Profugo(String nombre, int nivelInocencia, boolean nervioso) {
		this.nombre = nombre;
		this.nivelInocencia = nivelInocencia;
		this.nervioso = nervioso;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNivelInocencia() {
		return nivelInocencia;
	}

	public boolean esNervioso() {
		return nervioso;
	}

	public void volverNervioso() {
		this.nervioso = true;
	}

	public void calmarse() {
		this.nervioso = false;
	}

	
}
