package br.net.digitalzone.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.ProdutoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.ProdutoInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.ProdutoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.ProdutoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Produto;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.repository.ProdutoRepository;
import br.net.digitalzone.algafood.domain.service.CadastroProdutoService;
import br.net.digitalzone.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private  CadastroProdutoService cadastroProduto;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoDtoAssembler produtoDtoAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private AlgaLinks algaLinks;

	@SuppressWarnings("static-access")
	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		List<Produto> todosProdutos = null;
		
		if(incluirInativos) {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		}else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}

		return produtoDtoAssembler.toCollectionModel(todosProdutos)
	            .add(algaLinks.linkToProdutos(restauranteId));
	}
	
	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto =  cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		return produtoDtoAssembler.toModel(produto);
	}
	
	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId,
			@Valid @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		produto = cadastroProduto.salvar(produto);
		
		return produtoDtoAssembler.toModel(produto);
	}
	
	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid @RequestBody ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		produtoAtual = cadastroProduto.salvar(produtoAtual);
		
		return produtoDtoAssembler.toModel(produtoAtual);
	}


}
