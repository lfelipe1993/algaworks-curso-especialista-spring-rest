package br.net.digitalzone.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> {

	@ApiModelProperty(example = "1")
	//estou marcando a propriedade id com JsonView dizendo que é um resumo
	//@JsonView(value = { RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	@ApiModelProperty(example = "Thai Gourmet")
	//@JsonView(value = { RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	@ApiModelProperty(example = "12.00")
	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	private Boolean ativo;
	private Boolean aberto;
	
	/*{
	 * "id":1,
	 *	...
	 * "cozinha":{
	 * 	"id":2
	 * 	"nome": nome
	 * 	}
	 * }
	 */
	//@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	private EnderecoDTO endereco;
}
