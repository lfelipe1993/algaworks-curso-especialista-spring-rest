package br.net.digitalzone.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.net.digitalzone.algafood.api.v1.model.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissoes")
public interface PermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões")
	CollectionModel<PermissaoDTO> listar();

}