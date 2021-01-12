package br.net.digitalzone.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import br.net.digitalzone.algafood.api.v1.assembler.PedidoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.PedidoInputDisassembler;
import br.net.digitalzone.algafood.api.v1.assembler.PedidoResumoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.PedidoDTO;
import br.net.digitalzone.algafood.api.v1.model.PedidoResumoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.PedidoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.net.digitalzone.algafood.core.data.PageWrapper;
import br.net.digitalzone.algafood.core.data.PageableTranslator;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.net.digitalzone.algafood.domain.exception.NegocioException;
import br.net.digitalzone.algafood.domain.filter.PedidoFilter;
import br.net.digitalzone.algafood.domain.infraestructure.repository.spec.PedidoSpecs;
import br.net.digitalzone.algafood.domain.model.Pedido;
import br.net.digitalzone.algafood.domain.model.Usuario;
import br.net.digitalzone.algafood.domain.repository.PedidoRepository;
import br.net.digitalzone.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoDtoAssembler pedidoDtoAssembler;
	
	@Autowired
	private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoResumoDTO> pedidosModel = pedidoResumoDtoAssembler.toColletionDTO(pedidos);
//		
//		//instanciamos o enveloper (Wrapper) adicionando o model da representacao
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		//Instanciou o filterprovider, para adicinar o filtro
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}

	@Override
	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDtoAssembler);
	}

	@Override
	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		return pedidoDtoAssembler.toModel(pedido);
	}
	
	@Override
	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		
		try {
		Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		
		//TODO pegar usuario autenticado
		novoPedido.setCliente(new Usuario());
		novoPedido.getCliente().setId(algaSecurity.getUsuarioId());
		
		novoPedido = emissaoPedido.emitir(novoPedido);
		
		
		return pedidoDtoAssembler.toModel(novoPedido);
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		//DEPARA
		var mapeamento = ImmutableMap.of(
				"codigo","codigo",
				"nomerestaurante", "restaurante.nome",
				"cliente.nome","cliente.nome",
				"valorTotal","valorTotal"
				);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}

}
