package br.net.digitalzone.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Setter
@Getter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Joao da Silva")
    private String nome;
	@ApiModelProperty(example = "joao@digitalzone.net.br")
    private String email;
}
