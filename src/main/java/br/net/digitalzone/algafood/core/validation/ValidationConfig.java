package br.net.digitalzone.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
	
	@Bean
	//Estamos criando um metodo que produz um LocalValidatorFactoryBean
	//E estamos customizamos e indicando ue o ValidationMessageSource, é o messageSource da assinatura do metodo.
	// LocalValidatorFactoryBean -> faz integração e configuração do beanvalidation com o spring.
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		
		bean.setValidationMessageSource(messageSource);
		
		return bean;
	}
}
