package br.net.digitalzone.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe um cadastro de de foto do produto com código %d"
				+ "para o restaurante de código %d", produtoId,restauranteId));
	}

}
