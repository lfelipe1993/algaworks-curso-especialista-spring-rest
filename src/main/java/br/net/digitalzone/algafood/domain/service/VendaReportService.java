package br.net.digitalzone.algafood.domain.service;

import br.net.digitalzone.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
