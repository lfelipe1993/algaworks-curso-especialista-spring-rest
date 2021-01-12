package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.RestauranteProdutoController;
import br.net.digitalzone.algafood.api.v1.model.ProdutoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Produto;

@Component
public class ProdutoDtoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public ProdutoDtoAssembler() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}

	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
		modelMapper.map(produto, produtoDTO);

		if (algaSecurity.podeConsultarRestaurantes()) {
			produtoDTO.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

			produtoDTO.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
		}
		return produtoDTO;
	}

	@Override
	public CollectionModel<ProdutoDTO> toCollectionModel(Iterable<? extends Produto> entities) {
		return super.toCollectionModel(entities);
	}

}
