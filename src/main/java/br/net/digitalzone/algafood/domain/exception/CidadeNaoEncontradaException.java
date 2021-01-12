package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) EntidadeNaoEncontradaException ja tem essa notação
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CidadeNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe um cadastro de cidade com código %d", estadoId));
	}

}
