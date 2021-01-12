package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.GrupoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation(value = "Lista os Grupos de usuários")
	CollectionModel<GrupoDTO> listar();

	@ApiOperation(value = "Busca um grupo por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

	@ApiOperation(value = "Cadastra um grupo")
	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo Cadastrado com sucesso."), })
	GrupoDTO salvar(@ApiParam(name = "Corpo" ,value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);

	
	@ApiOperation(value = "Atualiza um grupo por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	GrupoDTO atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId, 
			@ApiParam(name = "Corpo" ,value = "Representação de um grupo com os novos dados", required = true) GrupoInput grupoInput);

	@ApiOperation(value = "Exclui um grupo por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Grupo excluido"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

}