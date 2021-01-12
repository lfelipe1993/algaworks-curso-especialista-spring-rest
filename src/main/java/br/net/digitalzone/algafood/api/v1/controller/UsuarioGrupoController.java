package br.net.digitalzone.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.GrupoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.GrupoDTO;
import br.net.digitalzone.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Usuario;
import br.net.digitalzone.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;

	@Autowired
	AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

		CollectionModel<GrupoDTO> gruposModel = grupoDtoAssembler.toCollectionModel(usuario.getGrupos()).removeLinks();

		if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
			gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

			gruposModel.getContent().forEach(grupoModel -> {
				grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(usuarioId, grupoModel.getId(), "dessasociar"));
			});
		}
		return gruposModel;
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.associarGrupo(usuarioId, grupoId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);

		return ResponseEntity.noContent().build();
	}

}
