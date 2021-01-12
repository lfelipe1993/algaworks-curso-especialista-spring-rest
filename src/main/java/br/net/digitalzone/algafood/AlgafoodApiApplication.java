package br.net.digitalzone.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.net.digitalzone.algafood.domain.infraestructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		//Se eu instanciar hora na minha aplicação vai pegar em UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
