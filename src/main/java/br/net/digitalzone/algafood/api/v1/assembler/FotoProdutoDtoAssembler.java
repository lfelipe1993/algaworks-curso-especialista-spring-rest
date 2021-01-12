package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.RestauranteProdutoFotoController;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.FotoProduto;
import br.net.digitalzone.algafood.domain.model.FotoProdutoDTO;

@Component
public class FotoProdutoDtoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public FotoProdutoDtoAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	}

	public FotoProdutoDTO toModel(FotoProduto foto) {
		FotoProdutoDTO fotoProdutoDTO = modelMapper.map(foto, FotoProdutoDTO.class);

		// Quem pode consultar restaurantes, também pode consultar os produtos e fotos
		if (algaSecurity.podeConsultarRestaurantes()) {
			fotoProdutoDTO.add(algaLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));

			fotoProdutoDTO.add(algaLinks.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
		}
		
		return fotoProdutoDTO;
	}
}
