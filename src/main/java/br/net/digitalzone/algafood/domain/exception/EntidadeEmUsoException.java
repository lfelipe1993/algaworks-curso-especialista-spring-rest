package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeEmUsoException(String msg) {
		super(msg);
	}
	

}
