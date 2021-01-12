package br.net.digitalzone.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.EntidadeEmUsoException;
import br.net.digitalzone.algafood.domain.exception.EstadoNaoEncontradoException;
import br.net.digitalzone.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.net.digitalzone.algafood.domain.model.Estado;
import br.net.digitalzone.algafood.domain.model.FormaPagamento;
import br.net.digitalzone.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_ESTADO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso.";
	@Autowired
	private FormaPagamentoRepository formasDePagamentoRepository;

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formasDePagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {
			formasDePagamentoRepository.deleteById(formaPagamentoId);
			formasDePagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, formaPagamentoId));
		}
	}
	
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formasDePagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}

}
