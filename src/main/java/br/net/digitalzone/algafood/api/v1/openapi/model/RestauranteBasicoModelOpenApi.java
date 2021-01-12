package br.net.digitalzone.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.net.digitalzone.algafood.api.v1.model.RestauranteBasicoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//Classe para documentação
@ApiModel("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {
	
	private RestaurantesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("RestaurantesEmbeddedModel")
	@Data
	public class RestaurantesEmbeddedModelOpenApi {

		private List<RestauranteBasicoDTO> restaurantes;

	}
}
