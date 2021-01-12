package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.RestauranteController;
import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Restaurante;

//Montar um Restaurante DTO
@Component
public class RestauranteDtoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteDtoAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteDTO);

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		}

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}

			if (restaurante.inativacaoPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}

		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}

			if (restaurante.fechamentoPermitido()) {
				restauranteDTO.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}

		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}

		if (algaSecurity.podeConsultarCidades()) {
			if (restaurante.getEndereco().getCidade().getId() != null) {
				restauranteDTO.getEndereco().getCidade()
						.add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		}
		
		return restauranteDTO;
	}

	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
	    CollectionModel<RestauranteDTO> collectionModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.podeConsultarRestaurantes()) {
	        collectionModel.add(algaLinks.linkToRestaurantes());
	    }
	    
	    return collectionModel;
	}

}
