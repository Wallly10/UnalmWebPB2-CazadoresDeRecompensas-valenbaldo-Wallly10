package ar.edu.unlam.pb2.dominio;

import ar.edu.unlam.pb2.excepciones.ExperienciaNegativaArgumentException;

public class CazadorSigiloso extends Cazador {

	public CazadorSigiloso(String nombre, int experienciaInicial) throws ExperienciaNegativaArgumentException {
		super(nombre, experienciaInicial);

	}

	@Override
	public Boolean condicionEspecifica(Profugo profugo) {
		return profugo.getHabilidad() < 50;
	}

	@Override
	public void intimidar(Profugo profugo) {
		profugo.reducirHabilidad(5);
	}

}
