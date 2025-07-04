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
	public void queNoSeCreeProfugoInvalido() {
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
	
	@Test
	public void queSeReduzcaCorrectamenteLaHabilidad() throws Exception {
		Profugo profugo = new Profugo("Habilidad", 10, true, 20);
		profugo.reducirHabilidad(5);
		assertEquals(Integer.valueOf(15), profugo.getHabilidad());
	}

	@Test
	public void queLaHabilidadNoBajeDeCero() throws Exception {
		Profugo profugo = new Profugo("MinHabilidad", 10, true, 3);
		profugo.reducirHabilidad(10);
		assertEquals(Integer.valueOf(0), profugo.getHabilidad());
	}

	@Test
	public void queSePuedaVolverNerviosoYLuegoCalmar() throws Exception {
		Profugo profugo = new Profugo("Nervioso", 10, false, 50);

		assertFalse(profugo.esNervioso());

		profugo.volverNervioso();
		assertTrue(profugo.esNervioso());

		profugo.calmarse();
		assertFalse(profugo.esNervioso());
	}

	@Test
	public void queDosProfugosConElMismoNombreSeanIguales() throws Exception {
		Profugo profugo1 = new Profugo("Carlos", 10, true, 50);
		Profugo profugo2 = new Profugo("Carlos", 5, false, 20);

		assertTrue(profugo1.equals(profugo2));
		assertEquals(profugo1.hashCode(), profugo2.hashCode());
	}

	@Test
	public void queDosProfugosConNombresDistintosNoSeanIguales() throws Exception {
		Profugo profugo1 = new Profugo("Carlos", 10, true, 50);
		Profugo profugo2 = new Profugo("Luis", 10, true, 50);

		assertFalse(profugo1.equals(profugo2));
	}

	@Test
	public void queEqualsDevuelvaFalseSiElObjetoEsNull() throws Exception {
		Profugo profugo = new Profugo("Carlos", 10, true, 50);
		assertFalse(profugo.equals(null));
	}

	@Test
	public void queEqualsDevuelvaFalseSiElObjetoEsDeOtraClase() throws Exception {
		Profugo profugo = new Profugo("Carlos", 10, true, 50);
		String otro = "Carlos";
		assertFalse(profugo.equals(otro));
	}

}