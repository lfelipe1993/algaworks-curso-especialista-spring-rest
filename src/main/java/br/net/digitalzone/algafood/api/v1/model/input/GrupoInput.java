package br.net.digitalzone.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
	@ApiModelProperty(example = "Gerete", required = true)
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
}
