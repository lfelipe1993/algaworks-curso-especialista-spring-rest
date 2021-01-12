package br.net.digitalzone.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.net.digitalzone.algafood.domain.exception.EntidadeEmUsoException;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import br.net.digitalzone.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com código %d";
	
	@Autowired
	CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();//descarrega todas operacoes pendentes no banco de dados.
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}
