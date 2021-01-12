package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import br.net.digitalzone.algafood.api.v1.model.input.CozinhaInput;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation(value = "Lista as cozinhas")
	PagedModel<CozinhaDTO> listar(Pageable pageable);
	
	@ApiOperation(value = "Busca uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da cozinha inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "cozinha não encontrada", response = Problem.class) })
	CozinhaDTO buscarCozinha(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

	@ApiOperation(value = "Cadastra uma cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cozinha Cadastrada com sucesso."), })
	CozinhaDTO adicionar(@ApiParam(name = "Corpo" ,value = "Representação de uma nova cozinha", required = true) CozinhaInput cozinhaInput);

	@ApiOperation(value = "Atualiza uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	Cozinha atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId, 
			@ApiParam(name = "Corpo" ,value = "Representação de uma cozinha com os novos dados", required = true) CozinhaInput cozinhaInput);

	@ApiOperation(value = "Exclui uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cozinha excluida"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

}