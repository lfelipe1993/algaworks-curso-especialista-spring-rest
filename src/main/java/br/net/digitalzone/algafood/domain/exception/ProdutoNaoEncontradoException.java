package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) EntidadeNaoEncontradaException ja tem essa notação
public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ProdutoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de produto com código %d", estadoId));
	}

}
