package br.net.digitalzone.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

	//@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Assis")
	private String nome;
	private EstadoDTO estado;

}
