package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.ProdutoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {
	
	private ProdutosEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	
	@ApiModel("ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModelOpenApi{
		private List<ProdutoDTO> produtos;
	}
}
