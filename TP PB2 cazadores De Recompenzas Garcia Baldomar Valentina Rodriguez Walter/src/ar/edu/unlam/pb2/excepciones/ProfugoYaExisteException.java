package ar.edu.unlam.pb2.excepciones;

@SuppressWarnings("serial")
public class ProfugoYaExisteException extends Exception {

	public ProfugoYaExisteException (String mensaje) {
		super(mensaje);
	}
}
