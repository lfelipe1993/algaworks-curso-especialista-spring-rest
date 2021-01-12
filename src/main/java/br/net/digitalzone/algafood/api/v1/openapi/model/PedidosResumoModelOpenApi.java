package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.PedidoResumoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosResumoModel")
@Setter
@Getter
public class PedidosResumoModelOpenApi {
	private PedidosResumoEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	private PageModelOpenApi page;
	
	@ApiModel("PedidosResumoEmbeddedModel")
	@Data
	public class PedidosResumoEmbeddedModelOpenApi{
		private List<PedidoResumoDTO> pedidos;
	}
}
