package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.FormaPagamentoInput;
import br.net.digitalzone.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista as formas de pagamento", response = FormasPagamentoModelOpenApi.class)
	ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);

	@ApiOperation(value = "Busca uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "forma de pagamento não encontrada", response = Problem.class) })
	ResponseEntity<FormaPagamentoDTO> buscar(ServletWebRequest request, 
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation(value = "Cadastra uma forma de pagamento")
	@ApiResponses({ @ApiResponse(code = 201, message = "Forma de pagamento Cadastrada com sucesso."), })
	FormaPagamentoDTO salvar(@ApiParam(name = "Corpo" ,value = "Representação de uma forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

	@ApiOperation(value = "Atualiza uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) })
	FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId, 
			@ApiParam(name = "Corpo" ,value = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoInput formaPagamentoInput);

	@ApiOperation(value = "Exclui uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Forma de pagamento excluida"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) })
	void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id);

}