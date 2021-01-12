package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.EstadoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.EstadoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation(value = "Lista os estados")
	CollectionModel<EstadoDTO> listar();

	@ApiOperation(value = "Busca um estado por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	EstadoDTO buscar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

	@ApiOperation(value = "Cadastra um estado")
	@ApiResponses({ @ApiResponse(code = 201, message = "Estado Cadastrado com sucesso."), })
	EstadoDTO salvar(
			@ApiParam(name = "Corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);

	@ApiOperation(value = "Atualiza um estado por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Estado atualizado"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	EstadoDTO atualizar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
			@ApiParam(name = "Corpo", value = "Representação de um grupo com os novos dados", required = true) EstadoInput estadoInput);

	@ApiOperation(value = "Exclui um estado por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Estado excluido"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

}