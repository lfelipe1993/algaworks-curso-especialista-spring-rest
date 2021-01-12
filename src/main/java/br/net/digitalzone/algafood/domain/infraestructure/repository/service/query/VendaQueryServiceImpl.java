package br.net.digitalzone.algafood.domain.infraestructure.repository.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.net.digitalzone.algafood.domain.filter.VendaDiariaFilter;
import br.net.digitalzone.algafood.domain.model.Pedido;
import br.net.digitalzone.algafood.domain.model.StatusPedido;
import br.net.digitalzone.algafood.domain.model.DTO.VendaDiaria;
import br.net.digitalzone.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,String timeOffset) {
		/*SELECT DATE(convert_tz(p.data_criacao,'+00:00','-03:00')) As data_criacao,
		 * count(p.id) as total_vendas,
		 * sum(p.valor_total) as total_faturado FROM pedido p
		 * WHERE p.status in ('CONFIRMADO','ENTREGUE')
		 * GROUP BY DATE(convert_tz(p.data_criacao,'+00:00','-03:00'))
		 */
		
		//Criar querys predicates etc...
		var builder = manager.getCriteriaBuilder();
		//criando um criteria builder com um tipo especificado (VendaDiaria) - retorno da consulta
		var query = builder.createQuery(VendaDiaria.class);
		//Especificando o From Pedido (SQL)
		var root = query.from(Pedido.class);
		
		//usa convert_tz do MYSQL
		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, 
				root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));
		
		//function vai criar uma expressao de uma funcao do SQL
		var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);
		//criar uma selecao correspondendo a um construtor (de uma classe "VendaDiaria")
		var selection = builder.construct(VendaDiaria.class, 
				functionDateDataCriacao, 
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		var predicates = new ArrayList<Predicate>();
		// adicionar pedicates no arraylist
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThan(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}
		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThan(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO,StatusPedido.ENTREGUE));
		

		//projetando selecao
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
