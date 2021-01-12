package br.net.digitalzone.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.net.digitalzone.algafood.api.v1.openapi.controller.EstatisticasControlerOpenApi;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.CheckSecurity;
import br.net.digitalzone.algafood.domain.filter.VendaDiariaFilter;
import br.net.digitalzone.algafood.domain.model.DTO.VendaDiaria;
import br.net.digitalzone.algafood.domain.service.VendaQueryService;
import br.net.digitalzone.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControlerOpenApi {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	} 

	@Override
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue="+00:00") String timeOffset){
		return vendaQueryService.consultarVendasDiarias(filtro,timeOffset);
	}
	
	@Override
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue="+00:00") String timeOffset){
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		//indica que o conteudo que estamos retornando na resposta ele deve ser baixado.
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}

}
