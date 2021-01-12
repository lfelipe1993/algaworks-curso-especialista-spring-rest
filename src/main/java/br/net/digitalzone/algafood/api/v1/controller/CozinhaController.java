package br.net.digitalzone.algafood.api.v1.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.net.digitalzone.algafood.api.v1.assembler.CozinhaDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.CozinhaInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import br.net.digitalzone.algafood.api.v1.model.input.CozinhaInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import br.net.digitalzone.algafood.domain.repository.CozinhaRepository;
import br.net.digitalzone.algafood.domain.service.CadastroCozinhaService;

//GET /cozinhas HTTP/1.1

@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {
	
	private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService CadastroCozinha;
	
	@Autowired
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	//Para dizer que eu quero fazer uma pré autorização(verifique antes se a expressão aqui é verdadeira)
	@CheckSecurity.Cozinhas.PodeConsultar//só pode listar cozinha se tiver autenticado
	@Override
	@GetMapping()
	public PagedModel<CozinhaDTO> listar(@PageableDefault(size=50) Pageable pageable) {
		logger.info("Consultando cozinhas...");

		//Retorna um fragmento da lista por conta da paginação
		Page<Cozinha> cozinhasPage =  cozinhaRepository.findAll(pageable);
		
		//Usamos para converter o page de cozinha para um pagedModel 
		//Converte de page para PagedModel quanto de cozinha para CozinhaDTO
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaDtoAssembler);
		
		return cozinhasPagedModel;
	}

	@CheckSecurity.Cozinhas.PodeConsultar//só pode listar cozinha se tiver autenticado
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscarCozinha(@PathVariable Long cozinhaId) {

		return cozinhaDtoAssembler.toModel(CadastroCozinha.buscarOuFalhar(cozinhaId));

	}

	//So pode executar esse metodo se a requisição atual estiver autenticada e a permissao EDITAR_COZINHA
	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
		return cozinhaDtoAssembler.toModel(CadastroCozinha.salvar(cozinha));
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinhaAtual = CadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return CadastroCozinha.salvar(cozinhaAtual);

	}

	@CheckSecurity.Cozinhas.PodeEditar
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		CadastroCozinha.excluir(id);

	}

}
