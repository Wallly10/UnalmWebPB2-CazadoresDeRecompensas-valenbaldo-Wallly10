package ar.edu.unlam.pb2.dominio;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unlam.pb2.excepciones.ExperienciaNegativaArgumentException;

public abstract class Cazador {

	protected String nombre;
	protected Integer experiencia;
	

	public Cazador(String nombre, Integer experienciaInicial) throws ExperienciaNegativaArgumentException {
		this.nombre = nombre;
		if (experienciaInicial < 0) {
		    throw new ExperienciaNegativaArgumentException("La experiencia no puede ser negativa");
		}
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
	
	public void capturar(Zona zona) {
	    Set<Profugo> profugosEnZona = new HashSet<>(zona.getProfugos());
	    Set<Profugo> capturados = new HashSet<>();
	    Set<Profugo> intimidados = new HashSet<>();

	    for (Profugo profugo : profugosEnZona) {
	        if (puedeCapturar(profugo)) {
	            capturados.add(profugo);
	        } else {
	            profugo.reducirInocenciaPorIntimidacion();
	            intimidar(profugo);
	            intimidados.add(profugo);
	        }
	    }

	    eliminarCapturadosDeLaZona(zona, capturados);
	    
	    Integer minimoHabilidad = calcularHabilidadMinima(intimidados);
	    this.experiencia += minimoHabilidad + (2 * capturados.size());

	}
	
	private void eliminarCapturadosDeLaZona(Zona zona, Set<Profugo> capturados) {
	    zona.getProfugos().removeAll(capturados);
	}
	
	private Integer calcularHabilidadMinima(Set<Profugo> intimidados) {
	    Integer minimo = null;

	    for (Profugo profugo : intimidados) {
	        if (minimo == null || profugo.getHabilidad() < minimo) {
	            minimo = profugo.getHabilidad();
	        }
	    }

	    if (minimo == null) {
	        return 0;
	    }
	    return minimo;
	}

	@Override
	public int hashCode() {
	    return nombre.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Cazador other = (Cazador) obj;
	    return nombre.equals(other.nombre);
	}
	
}
