package br.net.digitalzone.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.UsuarioDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.UsuarioInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.UsuarioDTO;
import br.net.digitalzone.algafood.api.v1.model.input.SenhaInput;
import br.net.digitalzone.algafood.api.v1.model.input.UsuarioComSenhaInput;
import br.net.digitalzone.algafood.api.v1.model.input.UsuarioInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Usuario;
import br.net.digitalzone.algafood.domain.repository.UsuarioRepository;
import br.net.digitalzone.algafood.domain.service.CadastroUsuarioService;

//GET /usuarios HTTP/1.1

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioDtoAssembler usuarioDtoAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> listar() {
		List<Usuario> todasUsuarios = usuarioRepository.findAll();

		return usuarioDtoAssembler.toCollectionModel(todasUsuarios);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar

	@Override
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		return usuarioDtoAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {

		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = cadastroUsuario.salvar(usuario);

		return usuarioDtoAssembler.toModel(usuario);

	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

		// BeanUtils.copyProperties(usuario, usuarioAtual, "id");
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

		return usuarioDtoAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroUsuario.excluir(id);

	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@Override
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {

		cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());

	}

}
