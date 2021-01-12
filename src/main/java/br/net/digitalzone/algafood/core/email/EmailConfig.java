package br.net.digitalzone.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.net.digitalzone.algafood.domain.service.EnvioEmailService;
import br.net.digitalzone.algafood.infrastructure.service.email.FakeEnvioEmailService;
import br.net.digitalzone.algafood.infrastructure.service.email.SandBoxEnvioEmailService;
import br.net.digitalzone.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {
	
	@Autowired
	EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
		case FAKE:
			return new FakeEnvioEmailService();
		case SMTP:
			return new SmtpEnvioEmailService();
		case SANDBOX:
			return new SandBoxEnvioEmailService();
		default:
			return null;
		}
	}

}
