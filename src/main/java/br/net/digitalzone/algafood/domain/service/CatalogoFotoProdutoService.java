package br.net.digitalzone.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.digitalzone.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import br.net.digitalzone.algafood.domain.model.FotoProduto;
import br.net.digitalzone.algafood.domain.repository.ProdutoRepository;
import br.net.digitalzone.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorage;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;

		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

		if (fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
		}

		// setando nome com UUID
		foto.setNomeArquivo(nomeNovoArquivo);
		// Primeiro tenta salvar no banco, pois se der problema no banco não salva no
		// diretorio a imagem.
		foto = produtoRepository.save(foto);
		produtoRepository.flush();// descarregar e salva tudo que esta na fila(Entity Manager JPA).

		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();

		fotoStorage.substituir(nomeArquivoExistente, novaFoto);

		return foto;
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {

		FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
		
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();

		fotoStorage.remover(fotoProduto.getNomeArquivo());
	}
	
	
	public FotoProduto buscarOuFalhar(Long restauranteId,Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}
	
}
