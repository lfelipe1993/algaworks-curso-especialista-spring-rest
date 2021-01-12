package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.CidadeDTO;
import br.net.digitalzone.algafood.api.v1.model.input.CidadeInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation(value = "Lista as cidades")
	CollectionModel<CidadeDTO> listar();

	@ApiOperation(value = "Busca uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@ApiOperation(value = "Cadastra uma cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade Cadastrada com sucesso."), })
	CidadeDTO salvar(@ApiParam(name = "Corpo" ,value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

	@ApiOperation(value = "Atualiza uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(name = "Corpo" ,value = "Representação de uma cidade com os novos dados", required = true) CidadeInput cidadeInput);

	@ApiOperation(value = "Exclui uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cidade excluida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

}