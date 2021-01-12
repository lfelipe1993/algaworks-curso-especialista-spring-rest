package br.net.digitalzone.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import br.net.digitalzone.algafood.domain.model.FormaPagamento;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.model.Usuario;
import br.net.digitalzone.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		

		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
		//quando a gente busca pelo buscar ou falhar ela fica num estado gerenciado pelo contexto de persistencia do JPA
		//qualquer mudança que a gente fizer no restauranteAtual ela vai ser sincronizada com o banco de dados.
	}

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
	}
	
	//Porque ta notado, pois se nao estiver notado, como estou fazendo uma operacoa de varias ativações
	//No primeiro deu certo, no segundo deu erro. O primeiro vai persistir e o segundo nao. Precisamos de uma operacao global
	@Transactional
	public void ativar(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::inativar);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId,Long formaPagamentoId) {
		Restaurante restaurante =buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId,Long formaPagamentoId) {
		Restaurante restaurante =buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.fechar();
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId,Long usuarioId) {
		Restaurante restaurante =buscarOuFalhar(restauranteId);
		
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId,Long usuarioId) {
		Restaurante restaurante =buscarOuFalhar(restauranteId);
		
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
	
}
