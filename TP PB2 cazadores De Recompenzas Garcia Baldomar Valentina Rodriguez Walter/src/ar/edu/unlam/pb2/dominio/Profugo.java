package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.HabilidadInvalidaException ;
import ar.edu.unlam.pb2.excepciones.InocenciaInvalidaException;

public class Profugo {

	private String nombre;
	private Integer nivelInocencia;
	private Boolean nervioso;
	private Integer habilidad;

	public Profugo(String nombre, Integer nivelInocencia, Boolean nervioso, Integer nivelHabilidad) throws HabilidadInvalidaException, InocenciaInvalidaException  {
		this.nombre = nombre;
		this.nivelInocencia = nivelInocencia;
		if(nivelInocencia<=0 ) {
			throw new InocenciaInvalidaException("La inocencia debe ser mayor a 0");
		}
		this.nervioso = nervioso;
		this.habilidad=nivelHabilidad;
		if (nivelHabilidad < 1 || nivelHabilidad > 100) {
		    throw new HabilidadInvalidaException("La habilidad debe estar entre 1 y 100");
		}
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public Integer getNivelInocencia() {
		return nivelInocencia;
	}

	public Boolean esNervioso() {
		return nervioso;
	}

	public void volverNervioso() {
		this.nervioso = true;
	}

	public void calmarse() {
		this.nervioso = false;
	}
	

	public Integer getHabilidad() {
		return habilidad;
	}


	public void reducirHabilidad(Integer reduccionHabilidad) {
		this.habilidad=Math.max(0, this.habilidad-reduccionHabilidad) ;
	}


	public void reducirInocenciaPorIntimidacion() {
		 this.nivelInocencia = Math.max(0, this.nivelInocencia - 2);
	}

	

	
}
