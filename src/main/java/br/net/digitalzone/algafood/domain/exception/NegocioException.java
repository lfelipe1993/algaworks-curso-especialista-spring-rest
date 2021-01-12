package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String msg) {
		super(msg);
	}

	public NegocioException(String msg, Throwable causa) {
		super(msg,causa);
	}
}
