package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("EstadosModel")
@Data
public class FormasPagamentoModelOpenApi {
	
	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	private LinksModelOpenApi _links;
	
	@ApiModel("FormasPagamentoEmbeddedModel")
	@Data
	public class FormasPagamentoEmbeddedModelOpenApi{
		private List<FormaPagamentoDTO> formasPagamento;
	}
}
