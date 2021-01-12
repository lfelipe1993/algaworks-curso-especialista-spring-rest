package br.net.digitalzone.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import br.net.digitalzone.algafood.api.v1.assembler.FormaPagamentoDtoAssembler;
import br.net.digitalzone.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.FormaPagamentoInput;
import br.net.digitalzone.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.model.FormaPagamento;
import br.net.digitalzone.algafood.domain.repository.FormaPagamentoRepository;
import br.net.digitalzone.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@Override
	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {

		// Desabilitamos o content-cache
		// Se a gente nao faz isso o filtro substitui o que vamos fazer aqui
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		Optional<OffsetDateTime> dataUltimaAtualizacao = formaPagamentoRepository.getLastUpdate();

		if (dataUltimaAtualizacao.isPresent()) {
			// Numero de segundos desde 1970 convertidos em string.
			eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
		}

		// compara o if-none-match com nosso eTag e retornar true/false
		if (request.checkNotModified(eTag)) {
			return null;
		}
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

		CollectionModel<FormaPagamentoDTO> formasPagamentosModel = 
			    formaPagamentoDtoAssembler.toCollectionModel(todasFormasPagamentos);

		return ResponseEntity.ok()
				// Cache-Control: Max-age=10
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)// desabilitamos o shallow e é
				// necessario adicionar o eTag
				// manualmente
				// .header("ETag", eTag)
				.body(formasPagamentosModel);
	}

	@Override
	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(ServletWebRequest request, @PathVariable Long formaPagamentoId) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		Optional<OffsetDateTime> dataUltimaAtualizacao = formaPagamentoRepository.getSingleLastUpdate(formaPagamentoId);

		if (dataUltimaAtualizacao.isPresent()) {
			// Numero de segundos desde 1970 convertidos em string.
			eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
		}

		// compara o if-none-match com nosso eTag e retornar true/false
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		
		FormaPagamentoDTO formaPagamento = formaPagamentoDtoAssembler
				.toModel(cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId));

		return ResponseEntity.ok()
				.eTag(eTag)
				// Cache-Control: Max-age=10
				// .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				// Se a resposta for cacheada, será necessário que faça uma validação no
				// servidor (Etag).
				// .cacheControl(CacheControl.noCache())
				// desativa o cache pra uma resposta.
				// .cacheControl(CacheControl.noStore())
				.body(formaPagamento);
	}

	@Override
	@CheckSecurity.FormasPagamento.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

		return formaPagamentoDtoAssembler.toModel(formaPagamento);

	}

	@Override
	@CheckSecurity.FormasPagamento.PodeEditar
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		// BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id");
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		return formaPagamentoDtoAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
	}

	@Override
	@CheckSecurity.FormasPagamento.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamento.excluir(id);

	}

}
