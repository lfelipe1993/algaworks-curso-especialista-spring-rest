package br.net.digitalzone.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.net.digitalzone.algafood.core.storage.StorageProperties.TipoStorage;
import br.net.digitalzone.algafood.domain.infraestructure.repository.service.storage.LocalFotoStorageService;
import br.net.digitalzone.algafood.domain.infraestructure.repository.service.storage.S3FotoStorageService;
import br.net.digitalzone.algafood.domain.service.FotoStorageService;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean//retorna a instancia de fotostorageservice
	public FotoStorageService fotoStorageService() {
		if(TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		}else {
			return new LocalFotoStorageService();
		}
	}
}
