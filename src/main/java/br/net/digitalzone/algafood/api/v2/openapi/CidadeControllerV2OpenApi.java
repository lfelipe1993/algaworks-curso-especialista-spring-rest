package br.net.digitalzone.algafood.api.v2.openapi;

import org.springframework.hateoas.CollectionModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v2.model.CidadeDtoV2;
import br.net.digitalzone.algafood.api.v2.model.input.CidadeInputV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeDtoV2> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
	CidadeDtoV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada"), })
	CidadeDtoV2 salvar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CidadeDtoV2 atualizar(Long cidadeId, CidadeInputV2 cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    Long cidadeId);

}