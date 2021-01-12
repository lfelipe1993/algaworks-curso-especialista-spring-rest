package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	private CozinhasEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi{
		private List<CozinhaDTO> cozinhas;
	}
}
