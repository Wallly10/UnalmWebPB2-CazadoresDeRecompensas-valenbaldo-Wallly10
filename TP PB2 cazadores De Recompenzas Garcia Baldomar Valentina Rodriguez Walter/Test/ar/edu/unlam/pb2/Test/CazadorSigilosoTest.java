package ar.edu.unlam.pb2.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unlam.pb2.dominio.CazadorSigiloso;
import ar.edu.unlam.pb2.dominio.Profugo;

public class CazadorSigilosoTest {

	@Test
	public void condicionEspecificaDevuelveTrueCuandoHabilidadEsMayorA50() throws Exception {
	    CazadorSigiloso sigiloso = new CazadorSigiloso("Luis", 10);
	    Profugo profugo = new Profugo("Profugo Con Habilidad Mayor a 50", 5, true, 51);
	    assertTrue(sigiloso.condicionEspecifica(profugo));
	}

	@Test
	public void condicionEspecificaDevuelveFalseCuandoHabilidadEs50oMenor() throws Exception {
	    CazadorSigiloso sigiloso = new CazadorSigiloso("Luis", 10);
	    Profugo profugo = new Profugo("Profugo Con Habilidad Menor o igual a 50", 5, true, 50);
	    assertFalse(sigiloso.condicionEspecifica(profugo));
	}

	@Test
	public void intimidarReduceHabilidadA5MenosPeroNoMenosDeCero() throws Exception {
	    CazadorSigiloso sigiloso = new CazadorSigiloso("Luis", 10);
	    Profugo profugo = new Profugo("P", 5, true, 6);
	    sigiloso.intimidar(profugo);
	    assertEquals(Integer.valueOf(1), profugo.getHabilidad());

	    sigiloso.intimidar(profugo);
	    assertEquals(Integer.valueOf(0), profugo.getHabilidad());
	    
	    sigiloso.intimidar(profugo);
	    assertEquals(Integer.valueOf(0), profugo.getHabilidad());
	}
	
	@Test
	public void siUnCazadorSigilosoIntimidaAunProfugoConHabilidadDe25LaHabilidadDelProfugoDa20() throws Exception {
	    CazadorSigiloso sigiloso = new CazadorSigiloso("Luis", 10);
	    Profugo profugo = new Profugo("P", 5, true, 25);
	    sigiloso.intimidar(profugo);
	    assertEquals(Integer.valueOf(20), profugo.getHabilidad());
	}
	

}
