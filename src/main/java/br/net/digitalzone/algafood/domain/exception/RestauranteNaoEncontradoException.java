package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) EntidadeNaoEncontradaException ja tem essa notação
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de restaurante com código %d", estadoId));
	}

}
