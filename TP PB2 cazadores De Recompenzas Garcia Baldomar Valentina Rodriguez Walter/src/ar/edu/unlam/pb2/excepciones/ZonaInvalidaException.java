package ar.edu.unlam.pb2.excepciones;

public class ZonaInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ZonaInvalidaException(String mensaje) {
		super(mensaje);
	}
}
