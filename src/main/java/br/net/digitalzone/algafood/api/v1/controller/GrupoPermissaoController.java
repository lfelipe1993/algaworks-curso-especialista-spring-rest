package br.net.digitalzone.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.PermissaoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.PermissaoDTO;
import br.net.digitalzone.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Grupo;
import br.net.digitalzone.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private PermissaoDtoAssembler permissaoDtoAssembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		CollectionModel<PermissaoDTO> permissoesModel = 
				permissaoDtoAssembler.toCollectionModel(grupo.getPermissoes())
				.removeLinks();
		
		permissoesModel.add(algaLinks.linkToGrupoPermissoes(grupoId));
		
				if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
					permissoesModel.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId,"associar"));
		
		permissoesModel.getContent().forEach(permissaoModel -> {
			permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
					grupoId, permissaoModel.getId(), "dessasociar"));
		});
		
				}
		return permissoesModel;
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.assasociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.dessasociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
