package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.dominio.EntrenamientoArtesMarciales;
import ar.edu.unlam.pb2.dominio.EntrenamientoDeProfugo;
import ar.edu.unlam.pb2.dominio.EntrenamientoElite;
import ar.edu.unlam.pb2.dominio.Profugo;
import ar.edu.unlam.pb2.dominio.ProteccionLegal;
import ar.edu.unlam.pb2.excepciones.HabilidadInvalidaException;
import ar.edu.unlam.pb2.excepciones.HabilidadMaximaException;
import ar.edu.unlam.pb2.excepciones.InocenciaDemasiadoAltaException;
import ar.edu.unlam.pb2.excepciones.InocenciaInvalidaException;
import ar.edu.unlam.pb2.excepciones.NoEsNerviosoException;

public class ProfugoEntrenamientoTest {

	@Test
	public void queElEntrenamientoDeArtesMarcialesDupliqueLaHabilidadHasta100()
			throws HabilidadInvalidaException, InocenciaInvalidaException, HabilidadMaximaException {
		Profugo profugo = new Profugo("Juan", 30, true, 40);
		EntrenamientoDeProfugo entrenado = new EntrenamientoArtesMarciales(profugo);

		assertEquals(Integer.valueOf(80), entrenado.getHabilidad());
		assertEquals(Integer.valueOf(30), entrenado.getNivelInocencia());
		assertTrue(entrenado.esNervioso());
	}

	@Test
	public void queLaHabilidadNoSupereElMaximoDe100ConArtesMarciales()
			throws HabilidadInvalidaException, InocenciaInvalidaException, HabilidadMaximaException {
		Profugo profugo = new Profugo("Luis", 30, false, 60);
		EntrenamientoDeProfugo entrenado = new EntrenamientoArtesMarciales(profugo);

		assertEquals(Integer.valueOf(100), entrenado.getHabilidad());
	}

	@Test
	public void queNoSePermitaEntrenarConArtesMarcialesSiYaTiene100DeHabilidad()
			throws HabilidadInvalidaException, InocenciaInvalidaException, HabilidadMaximaException {
		Profugo base = new Profugo("Carlos", 30, false, 100);
		try {
			new EntrenamientoArtesMarciales(base);
			fail("Se esperaba una HabilidadMaximaException");
		} catch (HabilidadMaximaException exception) {
			assertEquals("La habilidad ya está en el máximo", exception.getMessage());
		}
	}

	@Test
	public void queElEntrenamientoDeEliteHagaQueElProfugoNuncaSeaNervioso()
			throws HabilidadInvalidaException, InocenciaInvalidaException, NoEsNerviosoException {
		Profugo profugo = new Profugo("Pedro", 50, true, 35);
		EntrenamientoDeProfugo entrenado = new EntrenamientoElite(profugo);

		assertFalse(entrenado.esNervioso());
		assertEquals(Integer.valueOf(35), entrenado.getHabilidad());
		assertEquals(Integer.valueOf(50), entrenado.getNivelInocencia());
	}

	@Test
	public void queNoSePermitaEntrenamientoEliteSiYaNoEsNervioso()
			throws HabilidadInvalidaException, InocenciaInvalidaException, NoEsNerviosoException {
		Profugo profugo = new Profugo("Mateo", 40, false, 50);
		try {
			new EntrenamientoElite(profugo);
			fail("Se esperaba una NoEsNerviosoException");
		} catch (NoEsNerviosoException exception) {
			assertEquals("El prófugo ya no es nervioso", exception.getMessage());
		}
	}

	@Test
	public void queLaProteccionLegalMantengaInocenciaMinimaDe40()
			throws HabilidadInvalidaException, InocenciaInvalidaException, InocenciaDemasiadoAltaException {
		Profugo base = new Profugo("Mario", 25, false, 30);
		EntrenamientoDeProfugo entrenado = new ProteccionLegal(base);

		assertEquals(Integer.valueOf(40), entrenado.getNivelInocencia());
		assertEquals(Integer.valueOf(30), entrenado.getHabilidad());
		assertFalse(entrenado.esNervioso());
	}

	@Test
	public void queNoSePermitaProteccionLegalSiYaTieneInocenciaMayorOIgualA40()
			throws HabilidadInvalidaException, InocenciaInvalidaException, InocenciaDemasiadoAltaException {
		Profugo profugo = new Profugo("Lucia", 50, true, 30);
		try {
			new ProteccionLegal(profugo);
			fail("Se esperaba una InocenciaDemasiadoAltaException");
		} catch (InocenciaDemasiadoAltaException exception) {
			assertEquals("La inocencia ya es suficiente para protección legal", exception.getMessage());
		}
	}

	@Test
	public void queSePuedaCombinarTodosLosEntrenamientos()
			throws HabilidadInvalidaException, HabilidadMaximaException, InocenciaInvalidaException,
			InocenciaDemasiadoAltaException, NoEsNerviosoException, HabilidadMaximaException {
		Profugo profugo = new Profugo("Carlos", 20, true, 55);

		EntrenamientoDeProfugo superEntrenado = new ProteccionLegal(
				new EntrenamientoElite(new EntrenamientoArtesMarciales(profugo)));

		assertEquals("Carlos", superEntrenado.getNombre());
		assertEquals(Integer.valueOf(100), superEntrenado.getHabilidad());
		assertEquals(Integer.valueOf(40), superEntrenado.getNivelInocencia());
		assertFalse(superEntrenado.esNervioso());
	}

	@Test
	public void queNoSePuedaAplicarNingunEntrenamientoYSeLancenLasExcepcionesCorrespondientes()
			throws HabilidadInvalidaException, HabilidadMaximaException, InocenciaInvalidaException,
			InocenciaDemasiadoAltaException, NoEsNerviosoException {
		Profugo profugo = new Profugo("Carlos", 45, false, 100);
		try {
			new ProteccionLegal(new EntrenamientoElite(new EntrenamientoArtesMarciales(profugo)));
			fail("Se esperaba una excepción porque el prófugo no cumple ninguna condición para entrenar");
		} catch (HabilidadMaximaException excepctionHabilidad) {
			assertEquals("La habilidad ya está en el máximo", excepctionHabilidad.getMessage());
		} catch (NoEsNerviosoException exceptionNerviosismo) {
			assertEquals("El prófugo ya no es nervioso", exceptionNerviosismo.getMessage());
		} catch (InocenciaDemasiadoAltaException exceptionProteccion) {
			assertEquals("La inocencia ya es suficiente para protección legal", exceptionProteccion.getMessage());

		}
	}
	
	@Test
	public void queElEntrenamientoDeArtesMarcialesNoModifiqueLaInocenciaNiElNerviosismo()
	        throws HabilidadInvalidaException, InocenciaInvalidaException, HabilidadMaximaException {

	    Profugo base = new Profugo("Julieta", 35, true, 45);
	    EntrenamientoDeProfugo entrenado = new EntrenamientoArtesMarciales(base);

	    assertEquals(Integer.valueOf(90), entrenado.getHabilidad()); 
	    assertEquals(Integer.valueOf(35), entrenado.getNivelInocencia()); 
	    assertTrue(entrenado.esNervioso()); 
	}
	
	@Test
	public void queLosGettersFuncionenCorrectamenteConEntrenamientosCombinados()
	        throws HabilidadInvalidaException, InocenciaInvalidaException,
	               HabilidadMaximaException, NoEsNerviosoException, InocenciaDemasiadoAltaException {

	    Profugo base = new Profugo("Martina", 30, true, 45);

	    EntrenamientoDeProfugo combinado = new ProteccionLegal(
	                                            new EntrenamientoElite(
	                                                new EntrenamientoArtesMarciales(base)));

	    assertEquals("Martina", combinado.getNombre());
	    assertEquals(Integer.valueOf(90), combinado.getHabilidad()); 
	    assertEquals(Integer.valueOf(40), combinado.getNivelInocencia()); 
	    assertFalse(combinado.esNervioso()); 
	}
	
	@Test
	public void queLosEntrenamientosFuncionenIndependientementeDelOrden()
	        throws HabilidadInvalidaException, InocenciaInvalidaException,
	               HabilidadMaximaException, NoEsNerviosoException, InocenciaDemasiadoAltaException {

	    Profugo base = new Profugo("Lucas", 20, true, 40);

	    EntrenamientoDeProfugo combinado = new EntrenamientoArtesMarciales(
	                                            new ProteccionLegal(
	                                                new EntrenamientoElite(base)));

	    assertEquals("Lucas", combinado.getNombre());
	    assertEquals(Integer.valueOf(80), combinado.getHabilidad());
	    assertEquals(Integer.valueOf(40), combinado.getNivelInocencia());
	    assertFalse(combinado.esNervioso());
	}
	
}
