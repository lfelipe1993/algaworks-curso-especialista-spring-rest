package br.net.digitalzone.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.EntidadeEmUsoException;
import br.net.digitalzone.algafood.domain.exception.ProdutoNaoEncontradoException;
import br.net.digitalzone.algafood.domain.model.Produto;
import br.net.digitalzone.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	private static final String MSG_ESTADO_EM_USO = "Produto de código %d não pode ser removida, pois está em uso.";
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Transactional
	public void excluir(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
			produtoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(produtoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, produtoId));
		}
	}
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId ) {
		return produtoRepository.findById(restauranteId,produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}

}
