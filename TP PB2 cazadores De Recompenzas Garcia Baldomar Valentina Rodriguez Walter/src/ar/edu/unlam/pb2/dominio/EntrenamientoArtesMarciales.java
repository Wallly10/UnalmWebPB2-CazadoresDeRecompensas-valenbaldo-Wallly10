package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.HabilidadMaximaException;

public class EntrenamientoArtesMarciales implements EntrenamientoDeProfugo{

	private EntrenamientoDeProfugo profugo;

	public EntrenamientoArtesMarciales(EntrenamientoDeProfugo profugo) throws HabilidadMaximaException {
		if (profugo.getHabilidad() >= 100) {
			throw new HabilidadMaximaException("La habilidad ya está en el máximo");
		}
		this.profugo = profugo;
	}

	@Override
	public Integer getHabilidad() {
		return Math.min(profugo.getHabilidad() * 2, 100);
	}

	@Override
	public Integer getNivelInocencia() {
		return profugo.getNivelInocencia();
	}

	@Override
	public Boolean esNervioso() {
		return profugo.esNervioso();
	}

	@Override
	public String getNombre() {
		return profugo.getNombre();
	}
}
