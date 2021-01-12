package br.net.digitalzone.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksModelOpenApi {

	private LinkModel rel;
	
	@ApiModel("Link")
	@Getter
	@Setter
	private class LinkModel{
		private String href;
		private String templated;
	}
}
