package br.net.digitalzone.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.net.digitalzone.algafood.api.v1.assembler.FotoProdutoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.model.input.FotoProdutoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.net.digitalzone.algafood.domain.model.FotoProduto;
import br.net.digitalzone.algafood.domain.model.FotoProdutoDTO;
import br.net.digitalzone.algafood.domain.model.Produto;
import br.net.digitalzone.algafood.domain.service.CadastroProdutoService;
import br.net.digitalzone.algafood.domain.service.CatalogoFotoProdutoService;
import br.net.digitalzone.algafood.domain.service.FotoStorageService;
import br.net.digitalzone.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;

	@Autowired
	private FotoStorageService fotoStorage;

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput, @RequestPart(required = true) MultipartFile arquivo) throws IOException {

		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

		//MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

		return fotoProdutoDtoAssembler.toModel(fotoSalva);
	}

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

		return fotoProdutoDtoAssembler.toModel(fotoProduto);
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader("accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

			// Content-Type real
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			// parse do accept header.
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

			if (fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok().contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoFotoProduto.excluir(restauranteId, produtoId);
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
			throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}

	/*
	 * @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) public void
	 * atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
	 * 
	 * @Valid FotoProdutoInput fotoProdutoInput) {
	 * 
	 * var nomeArquivo = UUID.randomUUID().toString() + "_" +
	 * fotoProdutoInput.getArquivo().getOriginalFilename();
	 * 
	 * var arquivoFoto = Path.
	 * of("E:\\_Estudo e projetos\\AlgaWorks\\ESR\\_Projetos\\algafood-api\\catalogo"
	 * , nomeArquivo);
	 * 
	 * System.out.println(fotoProdutoInput.getDescricao());
	 * System.out.println(arquivoFoto);
	 * System.out.println(fotoProdutoInput.getArquivo().getContentType());
	 * 
	 * try { fotoProdutoInput.getArquivo().transferTo(arquivoFoto); } catch
	 * (Exception e) { throw new RuntimeException(); }
	 * 
	 * }
	 */
}
