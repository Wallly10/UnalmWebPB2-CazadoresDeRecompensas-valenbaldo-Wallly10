package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.dominio.CazadorRural;
import ar.edu.unlam.pb2.dominio.Profugo;

public class CazadorRuralTest {

	@Test
	public void condicionEspecificaDevuelveTrueCuandoProfugoEsNervioso() throws Exception {
	    CazadorRural rural = new CazadorRural("Pedro", 10);
	    Profugo profugo = new Profugo("P", 5, true, 30);
	    assertTrue(rural.condicionEspecifica(profugo));
	}

	@Test
	public void condicionEspecificaDevuelveFalseCuandoProfugoNoEsNervioso() throws Exception {
	    CazadorRural rural = new CazadorRural("Pedro", 10);
	    Profugo profugo = new Profugo("P", 5, false, 30);
	    assertFalse(rural.condicionEspecifica(profugo));
	}

	@Test
	public void intimidarHaceQueProfugoSePongaNervioso() throws Exception {
	    CazadorRural rural = new CazadorRural("Pedro", 10);
	    Profugo profugo = new Profugo("P", 5, false, 30);
	    rural.intimidar(profugo);
	    assertTrue(profugo.esNervioso());
	}
}
