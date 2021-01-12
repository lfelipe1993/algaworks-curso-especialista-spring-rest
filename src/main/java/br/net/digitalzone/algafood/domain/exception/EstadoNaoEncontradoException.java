package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) EntidadeNaoEncontradaException ja tem essa notação
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de estado com código %d", estadoId));
	}

}
