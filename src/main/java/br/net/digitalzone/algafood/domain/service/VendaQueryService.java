package br.net.digitalzone.algafood.domain.service;

import java.util.List;

import br.net.digitalzone.algafood.domain.filter.VendaDiariaFilter;
import br.net.digitalzone.algafood.domain.model.DTO.VendaDiaria;


public interface VendaQueryService {
	/*
	 * Interface: Porque a implementação dessa consulta é muito código de infraestrutura.
	 */
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,String timeOffset);
}
