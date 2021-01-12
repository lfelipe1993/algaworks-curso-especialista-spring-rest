package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.EstadoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {
	
	private EstadosEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	
	@ApiModel("EstadosEmbeddedModel")
	@Data
	public class EstadosEmbeddedModelOpenApi{
		private List<EstadoDTO> estados;
	}
}
