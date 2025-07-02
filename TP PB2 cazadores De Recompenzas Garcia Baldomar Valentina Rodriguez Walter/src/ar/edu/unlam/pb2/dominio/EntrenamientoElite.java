package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.NoEsNerviosoException;

public class EntrenamientoElite implements EntrenamientoDeProfugo{

	private EntrenamientoDeProfugo profugo;

	public EntrenamientoElite(EntrenamientoDeProfugo profugo) throws NoEsNerviosoException {
		if (!profugo.esNervioso()) {
			throw new NoEsNerviosoException("El prófugo ya no es nervioso");
		}
		this.profugo = profugo;
	}

	@Override
	public Integer getHabilidad() {
		return profugo.getHabilidad();
	}

	@Override
	public Integer getNivelInocencia() {
		return profugo.getNivelInocencia();
	}

	@Override
	public Boolean esNervioso() {
		return false;
	}

	@Override
	public String getNombre() {
		return profugo.getNombre();
	}
}
