package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.dominio.CazadorRural;
import ar.edu.unlam.pb2.dominio.CazadorSigiloso;
import ar.edu.unlam.pb2.dominio.CazadorUrbano;
import ar.edu.unlam.pb2.dominio.Profugo;
import ar.edu.unlam.pb2.dominio.Zona;
import ar.edu.unlam.pb2.excepciones.ExperienciaNegativaArgumentException;
import ar.edu.unlam.pb2.excepciones.HabilidadInvalidaException;
import ar.edu.unlam.pb2.excepciones.InocenciaInvalidaException;

public class CazadorTest {

	private CazadorSigiloso cazadorSigiloso;
	private CazadorUrbano cazadorUrbano;
	private CazadorRural cazadorRural;
	private Zona zona;

	@Before
	public void setup() throws ExperienciaNegativaArgumentException {
		zona = new Zona("ZonaTest");
		cazadorSigiloso = new CazadorSigiloso("Cazador Sigiloso", 30);
		cazadorRural = new CazadorRural("Cazador Rural", 30);
		cazadorUrbano = new CazadorUrbano("Cazador Urbano", 30);

	}

	@Test
	public void seCreaCazadorCorrectamente() {
		try {
			CazadorUrbano cazador = new CazadorUrbano("Cazador Valido", 10);
			assertNotNull(cazador);
			assertEquals("Cazador Valido", cazador.getNombre());
			assertEquals(Integer.valueOf(10), cazador.getExperiencia());
		} catch (ExperienciaNegativaArgumentException e) {
			fail("No se esperaba excepción");
		}
	}

	@Test
	public void noSePuedeCrearCazadorConExperienciaNegativa() {
		try {
			new CazadorUrbano("Invalido", -10);
			fail("Se esperaba una ExperienciaNegativaArgumentException");
		} catch (ExperienciaNegativaArgumentException e) {
			assertEquals("La experiencia no puede ser negativa", e.getMessage());
		}
	}

	@Test
	public void cazadorRuralpuedeCapturarSiProfugoEsNerviosoYtieneInocenciaMenoraExperiencia()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Jose", 20, true, 40);
		assertTrue(cazadorRural.puedeCapturar(profugo));
	}

	@Test
	public void cazadorRuralNoPuedeCapturarSiProfugoNoEsNervioso()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Jose", 20, false, 40);
		assertFalse(cazadorRural.puedeCapturar(profugo));
	}

	@Test
	public void cazadorRuralNoPuedeCapturarSiInocenciaEsMayorOIgualQueExperienciaAExperiencia()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Jose", 30, true, 40);
		assertFalse(cazadorRural.puedeCapturar(profugo));
	}

	@Test
	public void cazadorSigilosoPuedeCapturarSiHabilidadMenorA50EInocenciaMenorAExperiencia()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Luis", 20, true, 39);
		assertTrue(cazadorSigiloso.puedeCapturar(profugo));
	}

	@Test
	public void cazadorUrbanoPuedeCapturarSiProfugoNoEsNervioso()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Ana", 10, false, 50);
		assertTrue(cazadorUrbano.puedeCapturar(profugo));
	}

	@Test
	public void cazadorUrbanoCalmaAlProfugoAlIntimidarlo()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Ana", 10, true, 50);
		cazadorUrbano.intimidar(profugo);
		assertFalse(profugo.esNervioso());
	}

	@Test
	public void cazadorRuralVuelveNerviosoAlProfugo() throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Ana", 10, false, 50);
		cazadorRural.intimidar(profugo);
		assertTrue(profugo.esNervioso());
	}

	@Test
	public void cazadorSigilosoReduceHabilidadAlIntimidarA45()
			throws HabilidadInvalidaException, InocenciaInvalidaException {
		Profugo profugo = new Profugo("Luis", 10, true, 50);
		cazadorSigiloso.intimidar(profugo);
		assertEquals(Integer.valueOf(45), profugo.getHabilidad());
	}

	@Test
	public void cazadorRuralCapturaYIntimidaCorrectamenteLaXPDelCazadorSubeA72YlaInocenciaA48() throws Exception {
		Profugo jose = new Profugo("Jose", 5, true, 40);
		Profugo juan = new Profugo("Juan", 50, true, 40);

		zona.agregarProfugo(jose);
		zona.agregarProfugo(juan);

		cazadorRural.capturar(zona);

		assertFalse(zona.getProfugos().contains(jose));

		assertTrue(zona.getProfugos().contains(juan));
		assertEquals(Integer.valueOf(48), juan.getNivelInocencia());
		assertEquals(Integer.valueOf(72), cazadorRural.getExperiencia());
	}

	@Test
	public void cazadorCon30DeExperienciaTerminaCon62LuegoDeCaptura() throws Exception {

		Profugo capturable = new Profugo("Capturable", 5, true, 60);
		Profugo intimidable1 = new Profugo("Intimidable1", 20, false, 40);
		Profugo intimidable2 = new Profugo("Intimidable2", 25, false, 30);

		zona.agregarProfugo(capturable);
		zona.agregarProfugo(intimidable1);
		zona.agregarProfugo(intimidable2);

		cazadorRural.capturar(zona);

		assertFalse(zona.getProfugos().contains(capturable));
		assertTrue(zona.getProfugos().contains(intimidable1));
		assertTrue(zona.getProfugos().contains(intimidable2));

		assertEquals(Integer.valueOf(62), cazadorRural.getExperiencia());
	}

	@Test
	public void cazadorUrbanoCon30DeExperienciaTerminaCon94LuegoDeCaptura() throws Exception {

		Profugo capturable1 = new Profugo("Capturable1", 15, false, 70); 
		Profugo capturable2 = new Profugo("Capturable2", 20, false, 50); 
		Profugo intimidable = new Profugo("Intimidable", 25, true, 60);

		zona.agregarProfugo(capturable1);
		zona.agregarProfugo(capturable2);
		zona.agregarProfugo(intimidable);

		cazadorUrbano.capturar(zona);

		assertFalse(zona.getProfugos().contains(capturable1));
		assertFalse(zona.getProfugos().contains(capturable2));
		assertTrue(zona.getProfugos().contains(intimidable));

		assertEquals(Integer.valueOf(94), cazadorUrbano.getExperiencia());
	}

	@Test
	public void cazadorSigilosoCon30DeExperienciaCapturaDosYIntimidaUnoTerminaCon94LuegoDeCaptura() throws Exception {
		Profugo capturable1 = new Profugo("Capturable1", 10, true, 40);
		Profugo capturable2 = new Profugo("Capturable2", 5, false, 30); 
		Profugo intimidable = new Profugo("Intimidable", 35, true, 60); 

		zona.agregarProfugo(capturable1);
		zona.agregarProfugo(capturable2);
		zona.agregarProfugo(intimidable);

		cazadorSigiloso.capturar(zona);

		assertFalse(zona.getProfugos().contains(capturable1));
		assertFalse(zona.getProfugos().contains(capturable2));
		assertTrue(zona.getProfugos().contains(intimidable));

		assertEquals(Integer.valueOf(89), cazadorSigiloso.getExperiencia());
	}

}
