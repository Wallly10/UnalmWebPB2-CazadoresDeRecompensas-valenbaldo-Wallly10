package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pb2.dominio.*;
import ar.edu.unlam.pb2.excepciones.*;

import java.util.Set;

public class AgenciaTest {

	private Agencia agencia;
	private CazadorUrbano cazadorUrbano;
	private CazadorSigiloso cazadorSigiloso;
	private CazadorRural cazadorRural;
	private Zona zona1;
	private Zona zona2;
	private Profugo profugo1;
	private Profugo profugo2;
	private Profugo profugo3;

	@Before
	public void setup() throws Exception {
		agencia = new Agencia();

		cazadorUrbano = new CazadorUrbano("Urbano", 50);
		cazadorSigiloso = new CazadorSigiloso("Sigiloso", 40);
		cazadorRural = new CazadorRural("Rural", 30);

		zona1 = new Zona("Zona 1");
		zona2 = new Zona("Zona 2");

		profugo1 = new Profugo("Profugo 1", 10, false, 20);
		profugo2 = new Profugo("Profugo 2", 15, true, 40);
		profugo3 = new Profugo("Profugo 3", 5, false, 40);

		zona1.agregarProfugo(profugo1);
		zona1.agregarProfugo(profugo2);

		zona2.agregarProfugo(profugo3);

		agencia.registrarCazador(cazadorUrbano);
		agencia.registrarCazador(cazadorSigiloso);
		agencia.registrarCazador(cazadorRural);

		agencia.registrarZona(zona1);
		agencia.registrarZona(zona2);
	}

	@Test
	public void profugosConMismoNombreDebenSerConsideradosIguales() throws Exception {
		Profugo a = new Profugo("A", 5, false, 10);
		Profugo b = new Profugo("A", 7, true, 90);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void siIntentoRegistrarUnCazadorConNombreRepetidoSoloDebeGuardarseUnaVez() throws Exception {
		Cazador duplicado = new CazadorUrbano("Urbano", 20);
		agencia.registrarCazador(duplicado);
		int cantidad = 0;
		for (Cazador c : agencia.getCazadoresRegistrados()) {
			if (c.getNombre().equals("Urbano")) {
				cantidad++;
			}
		}
		assertEquals(1, cantidad);
	}

	@Test
	public void registrarCazadorDebeAgregarCazadorSinErroresYMapaInicializado() throws CazadorInvalidoException {
		Agencia agenciaNueva = new Agencia();
		try {
			CazadorUrbano nuevoCazador = new CazadorUrbano("Nuevo", 10);
			agenciaNueva.registrarCazador(nuevoCazador);

			assertTrue(agenciaNueva.getCazadoresRegistrados().contains(nuevoCazador));
			assertTrue(agenciaNueva.getRegistroDeCapturas().containsKey(nuevoCazador));
			assertTrue(agenciaNueva.getRegistroDeCapturas().get(nuevoCazador).isEmpty());
		} catch (ExperienciaNegativaArgumentException e) {
			fail("No debería lanzar excepción al crear cazador");
		}
	}

	@Test
	public void enviarCazadorUrbanoAZonaRealizaCapturaDelProfugoUnoYAlDosLosIntimidaDejandoSuIncoenciaA13YSinSerNervioso()
			throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);

		Set<Profugo> capturadosUrbano = agencia.getRegistroDeCapturas().get(cazadorUrbano);
		assertTrue(capturadosUrbano.contains(profugo1));
		assertFalse(capturadosUrbano.contains(profugo2));

		assertFalse(profugo2.esNervioso());
		assertTrue(profugo2.getNivelInocencia() == 13);

		assertFalse(zona1.getProfugos().contains(profugo1));
		assertTrue(zona1.getProfugos().contains(profugo2));

	}

	@Test
	public void enviarCazadorSigilosoAZonaCapturaEnZonaDosRegistraCorrectamente()
			throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		Set<Profugo> capturasSigiloso = agencia.getRegistroDeCapturas().get(cazadorSigiloso);
		assertTrue(capturasSigiloso.contains(profugo3));
		assertFalse(zona2.getProfugos().contains(profugo3));
	}

	@Test
	public void enviarCazadorUrbanoAZonaVaciaNoDebeCapturarNada() throws Exception {
		Zona vacia = new Zona("Ramos Mejia");
		agencia.registrarZona(vacia);

		agencia.enviarCazadorAZona(cazadorUrbano, vacia);
		Boolean noCapturo = agencia.getRegistroDeCapturas().get(cazadorUrbano).isEmpty();
		assertTrue(noCapturo);
	}

	@Test
	public void siCazadorEsEnviadoVariasVecesAZonaNoDebeDuplicarLasCapturas() throws Exception {
		Agencia agencia = new Agencia();
		Zona zona1 = new Zona("Centro");

		Profugo profugo = new Profugo("Carlos", 5, true, 40);
		zona1.agregarProfugo(profugo);

		Cazador cazadorUrbano = new CazadorUrbano("Luis", 10);

		agencia.registrarZona(zona1);
		agencia.registrarCazador(cazadorUrbano);

		agencia.enviarCazadorAZona(cazadorUrbano, zona1);
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);

		Set<Profugo> capturados = agencia.getRegistroDeCapturas().get(cazadorUrbano);
		assertEquals(1, capturados.size());
	}

	@Test
	public void obtenerTodosLosCapturadosDebeDevolverUnionDeCapturadosPorTodosLosCazadores()
			throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		Set<Profugo> todos = agencia.obtenerTodosLosCapturados();
		assertTrue(todos.contains(profugo1));
		assertTrue(todos.contains(profugo3));
		assertFalse(todos.contains(profugo2));
	}

	@Test
	public void obtenerMasHabilCapturadoDebeDevolverAlProfugoConMayorHabilidad()
			throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		Profugo masHabil = agencia.obtenerMasHabilCapturado();
		assertEquals(profugo3, masHabil);
	}

	@Test
	public void siNoHayCapturasObtenerMasHabilCapturadoDebeDevolverNull() {
		assertNull(agencia.obtenerMasHabilCapturado());
	}

	@Test
	public void obtenerCazadorConMasCapturasDebeDevolverElCazadorConMasProfugosCapturados() throws Exception {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		agencia.enviarCazadorAZona(cazadorRural, zona1);

		Profugo profugo4 = new Profugo("Profugo 4", 5, true, 15);
		zona2.agregarProfugo(profugo4);
		agencia.enviarCazadorAZona(cazadorRural, zona2);

		assertEquals(cazadorRural, agencia.obtenerCazadorConMasCapturas());
	}

	@Test
	public void siNoHayCapturasObtenerCazadorConMasCapturasDebeDevolverNull() {
		Agencia agenciaVacia = new Agencia();
		assertNull(agenciaVacia.obtenerCazadorConMasCapturas());
	}

	@Test
	public void siCazadorNoCapturaNingunProfugoNoDebeAgregarNadaAlRegistro() throws Exception {
		Profugo profugoDificil = new Profugo("Profugo dificil", 1, false, 60);
		Zona zona = new Zona("La Matanza");
		zona.agregarProfugo(profugoDificil);
		agencia.registrarZona(zona);
		agencia.enviarCazadorAZona(cazadorSigiloso, zona);

		Set<Profugo> capturas = agencia.getRegistroDeCapturas().get(cazadorSigiloso);

		assertTrue(capturas.isEmpty());

	}

	@Test
	public void profugoConHabilidadMaximaNoDebeSerCapturadoPorCazadorSigiloso() throws Exception {
		Profugo maximo = new Profugo("Profugo con habilidad maxima", 5, false, 100);
		zona1.agregarProfugo(maximo);

		agencia.enviarCazadorAZona(cazadorSigiloso, zona1);

		Set<Profugo> capturados = agencia.getRegistroDeCapturas().get(cazadorSigiloso);
		assertFalse(capturados.contains(maximo));
	}

	@Test
	public void agregarZonaNullDebeLanzarExcepcion() {
		try {
			agencia.registrarZona(null);
			fail("Se esperaba una ZonaInvalidaException");
		} catch (ZonaInvalidaException e) {
			assertEquals("La zona no puede ser null", e.getMessage());
		}
	}

	@Test
	public void registrarCazadorNullDebeLanzarExcepcion() {
		try {
			agencia.registrarCazador(null);
			fail("Se esperaba una CazadorInvalidoException");
		} catch (CazadorInvalidoException e) {
			assertEquals("El cazador no puede ser null", e.getMessage());
		}
	}

	@Test
	public void enviarCazadorNullAZonaDebeLanzarExcepcion() {
		try {
			agencia.enviarCazadorAZona(null, zona1);
			fail("Se esperaba una CazadorInvalidoException");
		} catch (CazadorInvalidoException e) {
			assertEquals("El cazador no puede ser null", e.getMessage());
		} catch (ZonaInvalidaException e) {
			fail("No se esperaba ZonaInvalidaException");
		}
	}

	@Test
	public void enviarCazadorAZonaNullDebeLanzarExcepcion() {
		try {
			agencia.enviarCazadorAZona(cazadorUrbano, null);
			fail("Se esperaba una ZonaInvalidaException");
		} catch (ZonaInvalidaException e) {
			assertEquals("La zona no puede ser null", e.getMessage());
		} catch (CazadorInvalidoException e) {
			fail("No se esperaba CazadorInvalidoException");
		}
	}

	@Test
	public void getZonasRegistradasIncluyeZonasAgregadasDinamicamente() throws Exception {
		Zona nueva = new Zona("Quilmes");
		agencia.registrarZona(nueva);

		Set<Zona> zonas = agencia.getZonasRegistradas();
		assertTrue(zonas.contains(nueva));
	}

	@Test
	public void dadoCazadorSinRegistroCuandoSeEnviaAZonaEntoncesSeInicializaCaptura() throws Exception {
		Cazador nuevo = new CazadorUrbano("Nuevo", 15);
		agencia.getCazadoresRegistrados().add(nuevo); 

		assertFalse(agencia.getRegistroDeCapturas().containsKey(nuevo));

		Profugo objetivo = new Profugo("Capturable", 5, false, 20);
		zona1.agregarProfugo(objetivo);

		agencia.enviarCazadorAZona(nuevo, zona1);

		assertTrue(agencia.getRegistroDeCapturas().containsKey(nuevo));
		assertTrue(agencia.getRegistroDeCapturas().get(nuevo).contains(objetivo));
	}

}
