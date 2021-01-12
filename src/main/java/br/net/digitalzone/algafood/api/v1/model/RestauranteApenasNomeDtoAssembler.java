package br.net.digitalzone.algafood.api.v1.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.RestauranteController;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeDtoAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteApenasNomeDtoAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeDTO.class);
	}

	@Override
	public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
		RestauranteApenasNomeDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteApenasNomeDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(algaLinks.linkToRestaurantes());
		}

		return collectionModel;
	}
}