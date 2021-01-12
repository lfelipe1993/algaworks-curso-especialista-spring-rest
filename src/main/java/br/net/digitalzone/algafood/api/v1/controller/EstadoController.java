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

import br.net.digitalzone.algafood.api.v1.assembler.EstadoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.EstadoInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.EstadoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.EstadoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Estado;
import br.net.digitalzone.algafood.domain.repository.EstadoRepository;
import br.net.digitalzone.algafood.domain.service.CadastroEstadoService;

//GET /estados HTTP/1.1

@RestController
@RequestMapping(path  = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDtoAssembler estadoDtoAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@Override
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		return estadoDtoAssembler.toCollectionModel(todosEstados);
	}

	@CheckSecurity.Estados.PodeConsultar
	@Override
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		return estadoDtoAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}

	@CheckSecurity.Estados.PodeConsultar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		estado = cadastroEstado.salvar(estado);
		
		return estadoDtoAssembler.toModel(estado);

	}

	@CheckSecurity.Estados.PodeEditar
	@Override
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		//BeanUtils.copyProperties(estado, estadoAtual, "id");
		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		return estadoDtoAssembler.toModel(cadastroEstado.salvar(estadoAtual));
	}

	@CheckSecurity.Estados.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstado.excluir(id);

	}
}
