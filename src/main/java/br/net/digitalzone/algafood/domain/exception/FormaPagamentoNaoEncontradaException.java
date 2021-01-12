package br.net.digitalzone.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) EntidadeNaoEncontradaException ja tem essa notação
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public FormaPagamentoNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe um cadastro de forma de pagamento com código %d", estadoId));
	}

}
