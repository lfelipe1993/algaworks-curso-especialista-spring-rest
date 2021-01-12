package br.net.digitalzone.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;

import br.net.digitalzone.algafood.api.exceptionHandler.Problem;
import br.net.digitalzone.algafood.api.v1.model.CidadeDTO;
import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import br.net.digitalzone.algafood.api.v1.model.EstadoDTO;
import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import br.net.digitalzone.algafood.api.v1.model.GrupoDTO;
import br.net.digitalzone.algafood.api.v1.model.PedidoResumoDTO;
import br.net.digitalzone.algafood.api.v1.model.PermissaoDTO;
import br.net.digitalzone.algafood.api.v1.model.ProdutoDTO;
import br.net.digitalzone.algafood.api.v1.model.RestauranteBasicoDTO;
import br.net.digitalzone.algafood.api.v1.model.UsuarioDTO;
import br.net.digitalzone.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.GruposModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.LinksModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.PageableModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.RestauranteBasicoModelOpenApi;
import br.net.digitalzone.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import br.net.digitalzone.algafood.api.v2.model.CidadeDtoV2;
import br.net.digitalzone.algafood.api.v2.model.CozinhaDtoV2;
import br.net.digitalzone.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import br.net.digitalzone.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2//habilitar suporte a OpenApi 2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	@Bean//registrar docket como componente spring
	public Docket apiDocketV1() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		//Sumario para criar um conjunto de serviço a serem documentado
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
				//Tudo que for de API no projeto pode inserir
				.apis(RequestHandlerSelectors.basePackage("br.net.digitalzone.algafood.api"))
				.paths(PathSelectors.ant("/v1/**"))
				//.paths(PathSelectors.ant("/restaurantes/*"));
				.build()
				.useDefaultResponseMessages(false)//removendo codigo de erro padrao
				//Configurando os status padrao para o metodo get.
	            .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
	            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//				.globalOperationParameters(Arrays.asList(
//				new ParameterBuilder()
//					.name("campos")
//					.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//					.parameterType("query")
//					.modelRef(new ModelRef("string"))
//					.build()))
	            //add modelo extra pra listar no models
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class
	            		,Resource.class,File.class,InputStream.class)
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
						CozinhasModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
						PedidosResumoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
						CidadesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
						EstadosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
						FormasPagamentoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
						GruposModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
						PermissoesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
						ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
					    RestauranteBasicoModelOpenApi.class))

					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
					        UsuariosModelOpenApi.class))
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
	            .apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usuários"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os Pedidos"),
						new Tag("Restaurantes", "Gerencia os Restaurantes"),
						new Tag("Estados", "Gerencia os Estados"),
						new Tag("Produtos", "Gerencia os Produtos"),
						new Tag("Usuarios", "Gerencia os Usuarios"),
						new Tag("Estatisticas", "Gerencia os Relatórios Estatisticos"),
						new Tag("Permissoes", "Gerencia as permissões"));//Vincula tags
	}
	
	@Bean//registrar docket como componente spring
	public Docket apiDocketV2() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		//Sumario para criar um conjunto de serviço a serem documentado
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.net.digitalzone.algafood.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.useDefaultResponseMessages(false)//removendo codigo de erro padrao
	            .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
	            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//				.globalOperationParameters(Arrays.asList(
//				new ParameterBuilder()
//					.name("campos")
//					.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//					.parameterType("query")
//					.modelRef(new ModelRef("string"))
//					.build()))
	            //add modelo extra pra listar no models
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class
	            		,Resource.class,File.class,InputStream.class)
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            	    typeResolver.resolve(PagedModel.class, CozinhaDtoV2.class),
	            	    CozinhasModelV2OpenApi.class))
	            	.alternateTypeRules(AlternateTypeRules.newRule(
	            	        typeResolver.resolve(CollectionModel.class, CidadeDtoV2.class),
	            	        CidadesModelV2OpenApi.class))
	            .apiInfo(apiInfoV2())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Cozinhas", "Gerencia as cozinhas"));//Vincula tags
	}
	
	@Override
	//Nesse metodo nos mapeamos caminhos para servir arquivos estaticos do SwaggerUI
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	private List<ResponseMessage> globalGetResponseMessages(){
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())//code erro
					.message("Erro interno do servidor")//msg
					.build(),
					new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor.")
					.build()
				);
	}
	
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.NOT_ACCEPTABLE.value())
	                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	                .message("Requisição recusada porque o corpo está em um formato não suportado")
	                .responseModel(new ModelRef("Problema"))
	                .build()
	        );
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build()
	        );
	}
	
	public ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("DigitalZone", "https://www.digitalzone.net.br", "contato@digitalzone.net.br"))
				.build();
	}
	
	public ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("2")
				.contact(new Contact("DigitalZone", "https://www.digitalzone.net.br", "contato@digitalzone.net.br"))
				.build();
	}
	
	//descreve a tecnica para proteger a API.
	public SecurityScheme securityScheme() {
		return new OAuthBuilder()
				.name("AlgaFood")
				.grantTypes(grantTypes())
				.scopes(scopes())
				.build();
	}
	
	private SecurityContext securityContext() {
		var securiyReference = SecurityReference.builder()
				.reference("AlgaFood")
				.scopes(scopes().toArray(new AuthorizationScope[0]))
				.build();
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(securiyReference))
				.forPaths(PathSelectors.any())
				.build();
	}
	
	private List<GrantType> grantTypes(){
		return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	}
	
	private List<AuthorizationScope> scopes(){
		return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
				new AuthorizationScope("WRITE", "Acesso de escirita"));
	}
}
