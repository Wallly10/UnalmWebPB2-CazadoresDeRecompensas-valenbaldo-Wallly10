package ar.edu.unlam.pb2.excepciones;

public class HabilidadInvalidaException  extends Exception {

	private static final long serialVersionUID = 2L;

	public HabilidadInvalidaException (String mensaje) {
		super(mensaje);
	}

}
