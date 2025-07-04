package ar.edu.unlam.pb2.dominio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.edu.unlam.pb2.excepciones.CazadorInvalidoException;
import ar.edu.unlam.pb2.excepciones.ZonaInvalidaException;

public class Agencia {

	private final Set<Cazador> cazadoresRegistrados;
	private final Set<Zona> zonasRegistradas;
	private final Map<Cazador, Set<Profugo>> registroDeCapturas;

	public Agencia() {
		this.cazadoresRegistrados = new HashSet<>();
		this.zonasRegistradas = new HashSet<>();
		this.registroDeCapturas = new HashMap<>();
	}

	public Set<Cazador> getCazadoresRegistrados() {
		return this.cazadoresRegistrados;
	}

	public Map<Cazador, Set<Profugo>> getRegistroDeCapturas() {
		return this.registroDeCapturas;
	}

	public Set<Zona> getZonasRegistradas() {
		return this.zonasRegistradas;
	}

	public void registrarCazador(Cazador cazador) throws CazadorInvalidoException {

		if (cazador == null) {
			throw new CazadorInvalidoException("El cazador no puede ser null");
		}

		this.cazadoresRegistrados.add(cazador);
		if (!this.registroDeCapturas.containsKey(cazador)) {
			this.registroDeCapturas.put(cazador, new HashSet<>());
		}
	}

	public void registrarZona(Zona zona) throws ZonaInvalidaException {
		if (zona == null) {
			throw new ZonaInvalidaException("La zona no puede ser null");
		}
		zonasRegistradas.add(zona);
	}

	public void enviarCazadorAZona(Cazador cazador, Zona zona) throws CazadorInvalidoException, ZonaInvalidaException {
		if (cazador == null) {
			throw new CazadorInvalidoException("El cazador no puede ser null");
		}
		if (zona == null) {
			throw new ZonaInvalidaException("La zona no puede ser null");
		}

		Set<Profugo> antesDeCaptura = new HashSet<>(zona.getProfugos());
		cazador.capturar(zona);
		Set<Profugo> despuesDeCaptura = zona.getProfugos();

		antesDeCaptura.removeAll(despuesDeCaptura);

		if (!this.registroDeCapturas.containsKey(cazador)) {
			this.registroDeCapturas.put(cazador, new HashSet<>());
		}

		Set<Profugo> capturasRegistradas = this.registroDeCapturas.get(cazador);
		antesDeCaptura.removeAll(capturasRegistradas);

		capturasRegistradas.addAll(antesDeCaptura);
	}

	public Set<Profugo> obtenerTodosLosCapturados() {
		Set<Profugo> capturados = new HashSet<>();
		for (Set<Profugo> conjunto : this.registroDeCapturas.values()) {
			capturados.addAll(conjunto);
		}
		return capturados;
	}

	public Profugo obtenerMasHabilCapturado() {
		Set<Profugo> capturados = obtenerTodosLosCapturados();
		if (capturados.isEmpty()) {
			return null;
		}

		Profugo masHabil = null;
		int habilidadMaxima = -1;

		for (Profugo profugo : capturados) {
			if (profugo.getHabilidad() > habilidadMaxima) {
				habilidadMaxima = profugo.getHabilidad();
				masHabil = profugo;
			}
		}

		return masHabil;
	}

	public Cazador obtenerCazadorConMasCapturas() {
		if (registroDeCapturas.isEmpty()) {
			return null;
		}

		Cazador cazadorConMasCapturas = null;
		int maxCapturas = -1;

		for (Map.Entry<Cazador, Set<Profugo>> entry : registroDeCapturas.entrySet()) {
			int cantidadCapturas = entry.getValue().size();
			if (cantidadCapturas > maxCapturas) {
				maxCapturas = cantidadCapturas;
				cazadorConMasCapturas = entry.getKey();
			}
		}

		return cazadorConMasCapturas;
	}
}
