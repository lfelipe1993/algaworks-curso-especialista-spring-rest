package br.net.digitalzone.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.net.digitalzone.algafood.api.v2.model.CidadeDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadesEmbeddedModelOpenApi {
        
        private List<CidadeDtoV2> cidades;
        
    }
    
}