package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.UsuarioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários responsaveis associadas a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	CollectionModel<UsuarioDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", 
            response = Problem.class)
    })
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", 
            response = Problem.class)
    })
	ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

}