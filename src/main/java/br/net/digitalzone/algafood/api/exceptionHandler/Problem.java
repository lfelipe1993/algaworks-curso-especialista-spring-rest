package br.net.digitalzone.algafood.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder//Builder padrão de projeto (um construtor da classe)
public class Problem {
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	@ApiModelProperty(example = "2020-06-22T23:38:56.3292457Z", position = 5)
	private OffsetDateTime timestamp;
	@ApiModelProperty(example = "https://algafood.com.br/mensagem-incompreensivel", position = 10)
	private String type;
	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 20)
	private String detail;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 25)
	private String userMessage;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
	private List<Object> objects;
	
	@ApiModel(value = "ObjetoProblema")
	@Getter
	@Builder
	public static class Object{
		@ApiModelProperty(example = "preco")
		private String name;
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
}