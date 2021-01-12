package br.net.digitalzone.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.assembler.FormaPagamentoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import br.net.digitalzone.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<FormaPagamentoDTO> formasPagamentoModel = formaPagamentoDtoAssembler
				.toCollectionModel(restaurante.getFormasPagamento()).removeLinks();

		formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
			formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));

			// COlocamos o collection model dentro de uma variavel no metodo
			// Assim extraimos o conteudo (que é uma coleção de formaPagamentoModel)
			// Fazemos um looping para colocar mais links (dessasociacao)
			formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
				formaPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamentoDessacociacao(restauranteId,
						formaPagamentoModel.getId(), "dessasociar"));
			});
		}
		return formasPagamentoModel;
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

}
