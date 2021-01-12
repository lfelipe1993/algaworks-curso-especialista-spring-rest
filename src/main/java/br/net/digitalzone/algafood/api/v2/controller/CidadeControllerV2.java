package br.net.digitalzone.algafood.api.v2.controller;

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
import br.net.digitalzone.algafood.api.v2.assembler.CidadeDtoAssemblerV2;
import br.net.digitalzone.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import br.net.digitalzone.algafood.api.v2.model.CidadeDtoV2;
import br.net.digitalzone.algafood.api.v2.model.input.CidadeInputV2;
import br.net.digitalzone.algafood.api.v2.openapi.CidadeControllerV2OpenApi;
import br.net.digitalzone.algafood.domain.exception.EstadoNaoEncontradoException;
import br.net.digitalzone.algafood.domain.exception.NegocioException;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.repository.CidadeRepository;
import br.net.digitalzone.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path ="/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDtoAssemblerV2 cidadeDtoAssembler;

	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<CidadeDtoV2> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeDtoAssembler.toCollectionModel(todasCidades);
	}

	
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDtoV2 buscar(@PathVariable Long cidadeId) {
		return cidadeDtoAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}

	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDtoV2 salvar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
		
		try {
			cidade = cadastroCidade.salvar(cidade);
			CidadeDtoV2 cidadeDTO = cidadeDtoAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getIdCidade());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDtoV2 atualizar(
			@PathVariable Long cidadeId,
			 @RequestBody @Valid CidadeInputV2 cidadeInput) {

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

	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCidade.excluir(id);
	}

}
