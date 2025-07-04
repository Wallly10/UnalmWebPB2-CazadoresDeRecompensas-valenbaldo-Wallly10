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
	public void enviarCazadorAZonaRealizaCapturaYActualizaRegistroCorrectamente() throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);

		Set<Profugo> capturadosUrbano = agencia.getRegistroDeCapturas().get(cazadorUrbano);
		assertTrue(capturadosUrbano.contains(profugo1));
		assertFalse(capturadosUrbano.contains(profugo2));

		assertFalse(profugo2.esNervioso());
		assertTrue(profugo2.getNivelInocencia() < 15);

		assertFalse(zona1.getProfugos().contains(profugo1));
		assertTrue(zona1.getProfugos().contains(profugo2));
	}

	@Test
	public void enviarCazadorAZonaCapturaEnDistintasZonasRegistraCorrectamente() throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		Set<Profugo> capturasSigiloso = agencia.getRegistroDeCapturas().get(cazadorSigiloso);
		assertTrue(capturasSigiloso.contains(profugo3));
		assertFalse(zona2.getProfugos().contains(profugo3));
	}

	@Test
	public void enviarCazadorAZonaVaciaNoDebeCapturarNada() throws Exception {
	    Zona vacia = new Zona("Ramos Mejia");
	    agencia.registrarZona(vacia);

	    agencia.enviarCazadorAZona(cazadorUrbano, vacia);

	    assertTrue(agencia.getRegistroDeCapturas().get(cazadorUrbano).isEmpty());
	}
	
	@Test
	public void siCazadorEsEnviadoVariasVecesAZonaNoDebeDuplicarLasCapturas() throws Exception {
	    agencia.enviarCazadorAZona(cazadorUrbano, zona1);
	    agencia.enviarCazadorAZona(cazadorUrbano, zona1);

	    Set<Profugo> capturados = agencia.getRegistroDeCapturas().get(cazadorUrbano);
	    assertEquals(1, capturados.size()); 
	}
	
	@Test
	public void obtenerTodosLosCapturadosDebeDevolverUnionDeCapturadosPorTodosLosCazadores() throws CazadorInvalidoException, ZonaInvalidaException {
		agencia.enviarCazadorAZona(cazadorUrbano, zona1);
		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);

		Set<Profugo> todos = agencia.obtenerTodosLosCapturados();
		assertTrue(todos.contains(profugo1));
		assertTrue(todos.contains(profugo3));
		assertFalse(todos.contains(profugo2));
	}

	@Test
	public void obtenerMasHabilCapturadoDebeDevolverAlProfugoConMayorHabilidad() throws CazadorInvalidoException, ZonaInvalidaException {
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
		zona2.agregarProfugo(profugoDificil);

		agencia.enviarCazadorAZona(cazadorSigiloso, zona2);
		Set<Profugo> capturas = agencia.getRegistroDeCapturas().get(cazadorSigiloso);

		assertFalse(capturas.contains(profugoDificil));
	}
	
	@Test
	public void capturarProfugoConHabilidadMinimaDebeSerPosible() throws Exception {
	    Profugo minimo = new Profugo("Profugo con habilidad minina", 5, false, 1);
	    zona1.agregarProfugo(minimo);

	    agencia.enviarCazadorAZona(cazadorSigiloso, zona1);

	    Set<Profugo> capturados = agencia.getRegistroDeCapturas().get(cazadorSigiloso);
	    assertTrue(capturados.contains(minimo));
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
	public void siUnCazadorSigilosoCapturaAUnProfugoConInocencia10YHabilidad60LaInocenciaQuedaEn8YLaHabilidadEn55() throws Exception {
	    Profugo original = new Profugo("Target", 10, false, 60);
	    zona1.agregarProfugo(original);

	    agencia.enviarCazadorAZona(cazadorSigiloso, zona1);

	    assertEquals((Integer)8, original.getNivelInocencia()); 
	    assertEquals((Integer)55, original.getHabilidad());     
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
}

