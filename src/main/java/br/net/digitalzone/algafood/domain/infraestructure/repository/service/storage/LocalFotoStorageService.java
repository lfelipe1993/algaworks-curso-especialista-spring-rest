package br.net.digitalzone.algafood.domain.infraestructure.repository.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import br.net.digitalzone.algafood.core.storage.StorageProperties;
import br.net.digitalzone.algafood.domain.service.FotoStorageService;


public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			//definimos onde será salva a foto (diretorio).
			Path arquivoPath = getArquivosPath(novaFoto.getNomeArquivo());
			//Define onde será salva o arquivo (copia e cola para o dir).
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}

	}
	
	@Override
	public void remover(String nomeArquivo) {
		Path arquivoPath = getArquivosPath(nomeArquivo);
		try {
			Files.deleteIfExists(arquivoPath);
		}  catch (Exception e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
		Path arquivoPath = getArquivosPath(nomeArquivo);
		var fotoRecuperada = FotoRecuperada.builder()
				.inputStream(Files.newInputStream(arquivoPath))
				.build();
		
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e);
		}
	}
	
	private Path getArquivosPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}
}
