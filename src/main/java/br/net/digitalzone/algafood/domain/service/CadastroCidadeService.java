package br.net.digitalzone.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.CidadeNaoEncontradaException;
import br.net.digitalzone.algafood.domain.exception.EntidadeEmUsoException;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Estado;
import br.net.digitalzone.algafood.domain.repository.CidadeRepository;
import br.net.digitalzone.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
//		Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
//				String.format("Não existe cadastro do estado com código %d", estadoId)));
		
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();//descarrega todas operacoes pendentes no banco de dados.
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_EM_USO, id));
		}
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}

}
