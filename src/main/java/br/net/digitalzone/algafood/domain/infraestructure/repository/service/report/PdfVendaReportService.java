package br.net.digitalzone.algafood.domain.infraestructure.repository.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.digitalzone.algafood.domain.filter.VendaDiariaFilter;
import br.net.digitalzone.algafood.domain.service.VendaQueryService;
import br.net.digitalzone.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Autowired
	private VendaQueryService vendaQueryService;

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

		try {
			// pegar um fluxo de dados de um arquivo dentro do meu projeto
			// this.getclass() dentro do meu projeto quero buscar um arquivo
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

			// parametros do relatorio (podemos passar ex: datainicial/datafinal)
			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
			// É um datasource de coleção de beans java
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			// Fill Report - esse metodo vai passar inputStream - fluxo de dados do
			// relatorio
			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diarias", e);
		}
	}

}
