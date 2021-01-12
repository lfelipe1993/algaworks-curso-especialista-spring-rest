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

import br.net.digitalzone.algafood.api.v1.assembler.GrupoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.GrupoInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.GrupoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.GrupoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Grupo;
import br.net.digitalzone.algafood.domain.repository.GrupoRepository;
import br.net.digitalzone.algafood.domain.service.CadastroGrupoService;

//GET /grupos HTTP/1.1

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE )
public class GrupoController implements GrupoControllerOpenApi  {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar() {
		List<Grupo> todosGrupos = grupoRepository.findAll();
		
		return grupoDtoAssembler.toCollectionModel(todosGrupos);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		return grupoDtoAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO salvar(@RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		grupo = cadastroGrupo.salvar(grupo);
		
		return grupoDtoAssembler.toModel(grupo);

	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

		//BeanUtils.copyProperties(grupo, grupoAtual, "id");
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

		return grupoDtoAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroGrupo.excluir(id);

	}

}
