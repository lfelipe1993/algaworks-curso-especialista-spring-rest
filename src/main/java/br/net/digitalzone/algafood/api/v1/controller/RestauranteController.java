package br.net.digitalzone.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.RestauranteBasicoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.RestauranteDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.RestauranteInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.RestauranteApenasNomeDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteApenasNomeDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.RestauranteBasicoDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteDTO;
import br.net.digitalzone.algafood.api.v1.model.input.RestauranteInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.exception.CidadeNaoEncontradaException;
import br.net.digitalzone.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.net.digitalzone.algafood.domain.exception.NegocioException;
import br.net.digitalzone.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.repository.RestauranteRepository;
import br.net.digitalzone.algafood.domain.service.CadastroRestauranteService;

@CrossOrigin(maxAge = 20)
@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteDtoAssembler restauranteModelAssembler;

	@Autowired
	RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	private RestauranteBasicoDtoAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteApenasNomeDtoAssembler restauranteApenasNomeModelAssembler;      

	// A partir de uma instancia injetada aqui, nos podemos fazer validacao dos
	// objetos.
	@Autowired
	private SmartValidator validator;

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteDTO> restaurantesDTO = restauranteModelAssembler.toColletionDTO(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDTO);
//		
//		//Por padrao virá resumido
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		}else if("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		
//		return restaurantesWrapper;
//	}

	@Override
	//@JsonView(RestauranteView.Resumo.class)
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<RestauranteBasicoDTO> listar() {
		return  restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	//@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")// passando parametro mostra a projecao resumida
	public CollectionModel<RestauranteApenasNomeDTO> listarApenasNome() {
		return restauranteApenasNomeModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
	}

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

		return restauranteModelAssembler.toModel(restaurante);

	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {

			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

		// Restaurante restaurante =
		// restauranteInputDisassembler.toDomainObject(restauranteInput);

		// a copia ja é uma conversar do restauranteInput para restaurante.
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

		// BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
		// "formasPagamento", "endereco", "dataCadastro",
		// "produtos");

		try {
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	// PUT /restaurante/{id}/ativar
	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarEmMassa(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestaurante.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	// DELETE /restaurante/{id}/imativar
	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarEmMassa(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestaurante.inativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
		return ResponseEntity.noContent().build();
		
	}

	/*
	 * @PatchMapping("/{restauranteId}") public RestauranteDTO
	 * atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String,
	 * Object> campos, HttpServletRequest request) {
	 * 
	 * Restaurante restauranteAtual =
	 * cadastroRestaurante.buscarOuFalhar(restauranteId);
	 * 
	 * merge(campos, restauranteAtual, request); validate(restauranteAtual,
	 * "restaurante");
	 * 
	 * 
	 * return atualizar(restauranteId, restauranteAtual);
	 * 
	 * }
	 * 
	 * private void validate(Restaurante restaurante, String ObjectName) {
	 * BeanPropertyBindingResult bindingResult = new
	 * BeanPropertyBindingResult(restaurante, ObjectName);
	 * 
	 * validator.validate(restaurante, bindingResult);
	 * 
	 * if (bindingResult.hasErrors()) { throw new ValidacaoException(bindingResult);
	 * }
	 * 
	 * }
	 * 
	 * private void merge(Map<String, Object> dadosOrigem, Restaurante
	 * restauranteDestino, HttpServletRequest request) { ServletServerHttpRequest
	 * serverHttpRequest = new ServletServerHttpRequest(request);
	 * 
	 * try { ObjectMapper objectMapper = new ObjectMapper();
	 * 
	 * // Configuramos para falhar se passar propriedades que estejam sendo
	 * ignoradas.
	 * objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
	 * true); // Caso a propriedade nao exista de fato, ela irá falhar.
	 * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	 * true);
	 * 
	 * Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem,
	 * Restaurante.class);
	 * 
	 * dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
	 * 
	 * Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
	 * field.setAccessible(true);
	 * 
	 * Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
	 * 
	 * // System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + //
	 * novoValor);
	 * 
	 * ReflectionUtils.setField(field, restauranteDestino, novoValor);
	 * 
	 * }); } catch (IllegalArgumentException e) { // vamos pegar a causa raiz
	 * Throwable rootCause = ExceptionUtils.getRootCause(e); // relançamos como a
	 * exceção abaixo. throw new HttpMessageNotReadableException(e.getMessage(),
	 * rootCause, serverHttpRequest);
	 * 
	 * } }
	 */

}
