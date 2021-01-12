package br.net.digitalzone.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.RestauranteApenasNomeDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteBasicoDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteDTO;
import br.net.digitalzone.algafood.api.v1.model.input.RestauranteInput;
import br.net.digitalzone.algafood.api.v1.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	// Fara apenas caso use jsonview. troca na documentação a projecao para o .class
	// do response abaixo
	@ApiOperation(value = "Lista de restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string") })
	CollectionModel<RestauranteBasicoDTO> listar();

	// ocultar essa operação pois quer mostrar o listar sem parametro
	@ApiIgnore
	@ApiOperation(value = "Lista Restaurante", hidden = true)
	CollectionModel<RestauranteApenasNomeDTO> listarApenasNome();

	@ApiOperation(value = "Busca um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "restaurante não encontrado", response = Problem.class) })
	RestauranteDTO buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	@ApiOperation(value = "Cadastra uma cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Restaurante Cadastrado com sucesso."), })
	RestauranteDTO adicionar(
			@ApiParam(name = "Corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

	@ApiOperation(value = "Atualiza um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	RestauranteDTO atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "Corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteInput);

	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso") })
	void ativarEmMassa(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantesIds);

	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes inativados com sucesso") })
	void inativarEmMassa(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantesIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}