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

import br.net.digitalzone.algafood.api.ResourceUriHelper;
import br.net.digitalzone.algafood.api.v1.assembler.CidadeDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.CidadeInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.CidadeDTO;
import br.net.digitalzone.algafood.api.v1.model.input.CidadeInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.exception.EstadoNaoEncontradoException;
import br.net.digitalzone.algafood.domain.exception.NegocioException;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.repository.CidadeRepository;
import br.net.digitalzone.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path ="/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDtoAssembler cidadeDtoAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@CheckSecurity.Cidades.PodeConsultar
	@Deprecated
	@Override
	@GetMapping
	public CollectionModel<CidadeDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeDtoAssembler.toCollectionModel(todasCidades);
	}

	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeDtoAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
		
		try {
			cidade = cadastroCidade.salvar(cidade);
			CidadeDTO cidadeDTO = cidadeDtoAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(
			@PathVariable Long cidadeId,
			 @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
			
			cidadeAtual = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			return cidadeDtoAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCidade.excluir(id);
	}

}
