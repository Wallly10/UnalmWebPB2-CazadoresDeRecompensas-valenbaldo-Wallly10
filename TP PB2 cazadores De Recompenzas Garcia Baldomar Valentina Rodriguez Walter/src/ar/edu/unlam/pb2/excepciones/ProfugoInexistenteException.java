package ar.edu.unlam.pb2.excepciones;

@SuppressWarnings("serial")
public class ProfugoInexistenteException extends Exception {

	public ProfugoInexistenteException (String mensaje) {
		super(mensaje);
	}
}