package br.net.digitalzone.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.net.digitalzone.algafood.api.v2.model.CozinhaDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelV2OpenApi {
    
    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV2OpenApi page;
    
    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {
        
        private List<CozinhaDtoV2> cozinhas;
        
    }
    
}
