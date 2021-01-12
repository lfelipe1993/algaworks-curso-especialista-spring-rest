package br.net.digitalzone.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private ApiRetirementHandler apiRetirementHandler;
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(AlgaMediaTypes.V2_APPLICATION_JSON);
	}
	
	//javax.servlet.Filter
	//Ao Receber uma requisição gera um hash da resposta e coloca o cabeçalho ETAG.
	//Ele tambem verifica se a ETAG da reposta é igual ao if-non-match da requisição do consumidor para retornar (304)
	@Bean
	public Filter shallowEtagheaderFilter(){
		return new ShallowEtagHeaderFilter();
	}
	
	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiRetirementHandler);
	}*/
}
