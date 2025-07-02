package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.InocenciaDemasiadoAltaException;

public class ProteccionLegal implements EntrenamientoDeProfugo{

	private EntrenamientoDeProfugo profugo;

	public ProteccionLegal(EntrenamientoDeProfugo profugo) throws InocenciaDemasiadoAltaException {
		if (profugo.getNivelInocencia() >= 40) {
			throw new InocenciaDemasiadoAltaException("La inocencia ya es suficiente para protección legal");
		}
		this.profugo = profugo;
	}

	@Override
	public Integer getHabilidad() {
		return profugo.getHabilidad();
	}

	@Override
	public Integer getNivelInocencia() {
		return Math.max(profugo.getNivelInocencia(), 40);
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
