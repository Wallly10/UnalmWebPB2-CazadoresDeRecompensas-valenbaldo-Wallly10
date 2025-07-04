package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.dominio.Profugo;
import ar.edu.unlam.pb2.dominio.Zona;
import ar.edu.unlam.pb2.excepciones.HabilidadInvalidaException;
import ar.edu.unlam.pb2.excepciones.InocenciaInvalidaException;
import ar.edu.unlam.pb2.excepciones.ProfugoInexistenteException;
import ar.edu.unlam.pb2.excepciones.ProfugoYaExisteException;

public class ZonaTest {

	@Test
	public void queSeCreeUnaZonaVacia() {
		Zona zona = new Zona("Nordelta");
		assertTrue(zona.getProfugos().isEmpty());
	}
	
	@Test
	public void queDevuelvaElNombreDeLaZona() {
		Zona zona = new Zona("Nordelta");
		assertEquals("Nordelta", zona.getNombre());;
	}
	
	@Test
	public void queSePuedaAgregarUnProfugoValido()
			throws HabilidadInvalidaException, InocenciaInvalidaException, ProfugoYaExisteException {
		Zona zona = new Zona("Cordoba");
		Profugo profugo = new Profugo("No me lo quiero cruzar", 20, true, 50);

		zona.agregarProfugo(profugo);

		assertEquals(1, zona.getProfugos().size());
		assertTrue(zona.getProfugos().contains(profugo));
	}

	@Test
	public void queNoSePuedaAgregarUnProfugoInvalido() throws HabilidadInvalidaException, InocenciaInvalidaException,
			ProfugoYaExisteException, ProfugoInexistenteException {
		Zona zona = new Zona("Cordoba");
		try {
			new Profugo("Invalido", -1, true, -1);
			fail("Se esperaba una Inocencia o habilidad Excepcion");
		} catch (InocenciaInvalidaException exception) {
			assertEquals("La inocencia debe ser mayor a 0", exception.getMessage());
			assertTrue(zona.getProfugos().isEmpty());
		} catch (HabilidadInvalidaException exception) {
			assertEquals("La habilidad debe estar entre 1 y 100", exception.getMessage());
			assertTrue(zona.getProfugos().isEmpty());
		}

	}

	@Test
	public void queNoSePuedaAgregarElMismoProfugoDosVeces()
			throws HabilidadInvalidaException, InocenciaInvalidaException, ProfugoYaExisteException {
		Zona zona = new Zona("Salta");
		Profugo profugo = new Profugo("Mas malo que el mal", 20, true, 50);

		try {
			zona.agregarProfugo(profugo);
			zona.agregarProfugo(profugo);
			fail("Se esperaba ProfugoYaExisteException");
		} catch (ProfugoYaExisteException exception) {
			assertEquals("El prófugo ya está en la zona", exception.getMessage());
		}
	}

	@Test
	public void queSePuedaEliminarUnProfugoExistente() throws HabilidadInvalidaException, InocenciaInvalidaException,
			ProfugoYaExisteException, ProfugoInexistenteException {
		Zona zona = new Zona("Mendoza");
		Profugo profugo = new Profugo("Re Malo", 20, true, 50);

		zona.agregarProfugo(profugo);
		zona.eliminarProfugo(profugo);

		assertFalse(zona.getProfugos().contains(profugo));
		assertTrue(zona.getProfugos().isEmpty());
	}

	@Test
	public void queNoSePuedaEliminarUnProfugoInexistente() throws HabilidadInvalidaException,
			InocenciaInvalidaException, ProfugoYaExisteException, ProfugoInexistenteException {
		Zona zona = new Zona("Tucumán");
		Profugo profugo = new Profugo("Malo", 20, true, 50);

		try {
			zona.eliminarProfugo(profugo);
			fail("Se esperaba ProfugoInexistenteException");
		} catch (ProfugoInexistenteException exception) {
			assertEquals("El prófugo no se encuentra en la zona", exception.getMessage());
		}
	}

}
