package br.net.digitalzone.algafood.domain.infraestructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.net.digitalzone.algafood.domain.filter.PedidoFilter;
import br.net.digitalzone.algafood.domain.model.Pedido;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {

			if (Pedido.class.equals(query.getResultType())) {
				// resolvendo problema do N+1 na query
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}

			var predicates = new ArrayList<Predicate>();

			// adicionar pedicates no arraylist
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThan(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThan(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
