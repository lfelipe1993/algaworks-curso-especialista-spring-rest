package br.net.digitalzone.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadeModel")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDtoV2 extends RepresentationModel<CidadeDtoV2> {

	// @ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long idCidade;
	@ApiModelProperty(example = "Assis")
	private String nomeCidade;

	private long idEstado;
	private String nomeEstado;

}
