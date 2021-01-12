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

import br.net.digitalzone.algafood.api.v1.assembler.UsuarioDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.UsuarioDTO;
import br.net.digitalzone.algafood.api.v1.openapi.controller.RestauranteUsuarioControllerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioDtoAssembler usuarioDtoAssembler;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioDTO> usuariosModel = usuarioDtoAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks();

		usuariosModel.add(algaLinks.linkToResponsaveisRestaurante(restauranteId));

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			usuariosModel.add(algaLinks.linkToRestauranteResponsaveisAssociacao(restauranteId, "associar"));

			usuariosModel.getContent().forEach(usuarioModel -> {
				usuarioModel.add(algaLinks.linkToRestauranteResponsaveisDessacociacao(restauranteId,
						usuarioModel.getId(), "dessasociar"));
			});
		}
		return usuariosModel;
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

}
