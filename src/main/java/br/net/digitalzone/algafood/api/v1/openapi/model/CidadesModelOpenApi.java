package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.CidadeDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {
	
	private CidadeEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi{
		private List<CidadeDTO> cidades;
	}
}
