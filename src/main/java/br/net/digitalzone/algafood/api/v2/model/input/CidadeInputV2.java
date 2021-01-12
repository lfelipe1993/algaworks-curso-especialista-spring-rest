package br.net.digitalzone.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadeInput")
@Getter
@Setter
public class CidadeInputV2 {
	@ApiModelProperty(example = "Assis", required = true)
	@NotBlank
	private String nomeCidade;
	
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long idEstado;
	
}
