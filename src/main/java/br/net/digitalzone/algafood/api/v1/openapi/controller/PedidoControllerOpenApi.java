package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.PedidoDTO;
import br.net.digitalzone.algafood.api.v1.model.PedidoResumoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.PedidoInput;
import br.net.digitalzone.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula"
				,name = "campos", paramType = "query", type = "string")
	})
	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula"
				,name = "campos", paramType = "query", type = "string")
	})
	@ApiOperation(value = "Busca um pedido por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do pedido inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class) })
	PedidoDTO buscar(@ApiParam(value = "ID de um pedido", example = "1", required = true) String codigoPedido);

	@ApiOperation(value = "Cadastra um pedido")
	@ApiResponses({ @ApiResponse(code = 201, message = "Pedido cadastrado com sucesso."), })
	PedidoDTO adicionar(@ApiParam(name = "Corpo" ,value = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);

}