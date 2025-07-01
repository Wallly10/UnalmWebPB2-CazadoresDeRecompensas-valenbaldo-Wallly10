package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.dominio.CazadorUrbano;
import ar.edu.unlam.pb2.dominio.Profugo;

public class CazadorUrbanoTest {
	

	@Test
	public void condicionEspecificaDevuelveTrueCuandoProfugoNoEsNervioso() throws Exception {
	    CazadorUrbano urbano = new CazadorUrbano("Juan", 10);
	    Profugo profugo = new Profugo("Profugo que no es nervioso", 5, false, 30);
	    assertTrue(urbano.condicionEspecifica(profugo));
	}
	
	@Test
	public void condicionEspecificaDevuelveFalseCuandoProfugoEsNervioso() throws Exception {
	    CazadorUrbano urbano = new CazadorUrbano("Juan", 10);
	    Profugo profugo = new Profugo("Profugo que es nervioso", 5, true, 30);
	    assertFalse(urbano.condicionEspecifica(profugo));
	}
	
	@Test
	public void intimidarHaceQueProfugoSeCalme() throws Exception {
	    CazadorUrbano urbano = new CazadorUrbano("Juan", 10);
	    Profugo profugo = new Profugo("Profugo", 5, true, 30);
	    urbano.intimidar(profugo);
	    assertFalse(profugo.esNervioso());
	}
}
