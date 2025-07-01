package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;
import ar.edu.unlam.pb2.dominio.Profugo;
import ar.edu.unlam.pb2.excepciones.HabilidadInvalidaException;
import ar.edu.unlam.pb2.excepciones.InocenciaInvalidaException;

public class ProfugoTest {

	@Test
	public void queNoSePermitaCrearProfugoConHabilidadInvalida() {
		try {
			new Profugo("Malo", 10, true, 150);
			fail("Se esperaba una HabilidadInvalidaException");
		} catch (HabilidadInvalidaException e) {
			assertEquals("La habilidad debe estar entre 1 y 100", e.getMessage());
		} catch (InocenciaInvalidaException e) {
			fail("No se esperaba InocenciaInvalidaException");
		}
	}

	@Test
	public void queNoSePermitaCrearProfugoConInocenciaNegativa() {
		try {
			new Profugo("Malo", 0, true, 50);
			fail("Se esperaba una InocenciaInvalidaException");
		} catch (InocenciaInvalidaException e) {
			assertEquals("La inocencia debe ser mayor a 0", e.getMessage());
		} catch (HabilidadInvalidaException e) {
			fail("No se esperaba HabilidadInvalidaException");
		}
	}

	@Test
	public void queSeCreeProfugoValido() {
		try {
			Profugo profugo = new Profugo("Valido", 20, true, 50);
			assertNotNull(profugo);
			assertEquals("Valido", profugo.getNombre());
			assertEquals(Integer.valueOf(20), profugo.getNivelInocencia());
			assertTrue(profugo.esNervioso());
			assertEquals(Integer.valueOf(50), profugo.getHabilidad());
		} catch (HabilidadInvalidaException | InocenciaInvalidaException e) {
			fail("No se esperaba ninguna excepción");
		}
	}

	@Test
	public void queNoSeCreeProfugoIValido() {
		try {
			new Profugo("Invalido", -1, true, -1);
			fail("Se esperaba una excepción por inocencia o habilidad inválida");
		} catch (InocenciaInvalidaException e) {
			assertEquals("La inocencia debe ser mayor a 0", e.getMessage());
		} catch (HabilidadInvalidaException e) {
			assertEquals("La habilidad debe estar entre 1 y 100", e.getMessage());
		}
	}

	@Test
	public void queLaInocenciaNoSeaNegativa() {
		try {
			Profugo profugo = new Profugo("Profugo de prueba", 1, true, 50);
			profugo.reducirInocenciaPorIntimidacion();
			assertEquals(Integer.valueOf(0), profugo.getNivelInocencia());

			profugo.reducirInocenciaPorIntimidacion(); 
			assertEquals(Integer.valueOf(0), profugo.getNivelInocencia()); 
		} catch (HabilidadInvalidaException | InocenciaInvalidaException e) {
			fail("No se esperaba ninguna excepción");
		}
	}
}