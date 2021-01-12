package br.net.digitalzone.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.EntidadeEmUsoException;
import br.net.digitalzone.algafood.domain.exception.NegocioException;
import br.net.digitalzone.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.net.digitalzone.algafood.domain.model.Grupo;
import br.net.digitalzone.algafood.domain.model.Usuario;
import br.net.digitalzone.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removida, pois está em uso.";
	private static final String EMAIL_JA_EXISTENTE = "Já existe um usário cadastrado com e-mail %s";
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	

	@Transactional
	public Usuario salvar(Usuario usuario) {
		//JPA para de gerenciar o usuario em questao.
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format(EMAIL_JA_EXISTENTE, usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void excluir(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    
	    if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
	        throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
	    }
	    
	    usuario.setSenha(passwordEncoder.encode(novaSenha));
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

}
