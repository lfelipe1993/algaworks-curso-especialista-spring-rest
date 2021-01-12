package br.net.digitalzone.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhaModel")
@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDtoV2 extends RepresentationModel<CozinhaDtoV2> {
	
	@ApiModelProperty(example = "1")
//	@JsonView(RestauranteView.Resumo.class)
	private Long idCozinha;
	@ApiModelProperty(example = "Brasileira")
	//@JsonView(RestauranteView.Resumo.class)
	private String nomeCozinha;

}
