package br.net.digitalzone.algafood.domain.infraestructure.repository.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.net.digitalzone.algafood.core.storage.StorageProperties;
import br.net.digitalzone.algafood.domain.service.FotoStorageService;


public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			var caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			var objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(novaFoto.getContentType());

			var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo,
					novaFoto.getInputStream(), objectMetaData).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar o arquivo para AmazonS3", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			var caminhoArquivo = getCaminhoArquivo(nomeArquivo);

			var putObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
					
			amazonS3.deleteObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível remover o arquivo para AmazonS3", e);
		}

	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		return FotoRecuperada.builder().url(url.toString()).build();
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

}
