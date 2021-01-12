package br.net.digitalzone.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import br.net.digitalzone.algafood.api.v2.assembler.CozinhaDtoAssemblerV2;
import br.net.digitalzone.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import br.net.digitalzone.algafood.api.v2.model.CozinhaDtoV2;
import br.net.digitalzone.algafood.api.v2.model.input.CozinhaInputV2;
import br.net.digitalzone.algafood.api.v2.openapi.CozinhaControllerV2OpenApi;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import br.net.digitalzone.algafood.domain.repository.CozinhaRepository;
import br.net.digitalzone.algafood.domain.service.CadastroCozinhaService;

//GET /cozinhas HTTP/1.1

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService CadastroCozinha;
	
	@Autowired
	private CozinhaDtoAssemblerV2 cozinhaDtoAssembler;
	
	@Autowired
	private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	
	@Override
	@GetMapping()
	public PagedModel<CozinhaDtoV2> listar(@PageableDefault(size=50) Pageable pageable) {
		//Retorna um fragmento da lista por conta da paginação
		Page<Cozinha> cozinhasPage =  cozinhaRepository.findAll(pageable);
		
		//Usamos para converter o page de cozinha para um pagedModel 
		//Converte de page para PagedModel quanto de cozinha para CozinhaDtoV2
		PagedModel<CozinhaDtoV2> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaDtoAssembler);
		
		return cozinhasPagedModel;
	}

	
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaDtoV2 buscarCozinha(@PathVariable Long cozinhaId) {

		return cozinhaDtoAssembler.toModel(CadastroCozinha.buscarOuFalhar(cozinhaId));

	}

	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDtoV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
		return cozinhaDtoAssembler.toModel(CadastroCozinha.salvar(cozinha));
	}

	
	@Override
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {

		Cozinha cozinhaAtual = CadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return CadastroCozinha.salvar(cozinhaAtual);

	}

	
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		CadastroCozinha.excluir(id);

	}

}
