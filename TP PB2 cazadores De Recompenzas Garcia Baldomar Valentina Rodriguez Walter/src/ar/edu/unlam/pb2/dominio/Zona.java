package ar.edu.unlam.pb2.dominio;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unlam.pb2.excepciones.ProfugoInexistenteException;
import ar.edu.unlam.pb2.excepciones.ProfugoYaExisteException;

public class Zona {

	private String nombre;
	private Set<Profugo> profugos = new HashSet<>();

	public Zona(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<Profugo> getProfugos() {
		return profugos;
	}

	
	public void agregarProfugo(Profugo profugoAAgregar) throws ProfugoYaExisteException {
		if (!this.profugos.add(profugoAAgregar)) {
			throw new ProfugoYaExisteException("El prófugo ya está en la zona");
		}
	}

	public void eliminarProfugo(Profugo profugoAEliminar) throws ProfugoInexistenteException {
		if (!this.profugos.remove(profugoAEliminar)) {
			throw new ProfugoInexistenteException("El prófugo no se encuentra en la zona");
		}
	}
}
