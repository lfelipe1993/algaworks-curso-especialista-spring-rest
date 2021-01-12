package br.net.digitalzone.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

	public static Pageable translate(Pageable pageable, Map<String,String> fieldsMapping) {

		//nomeCliente: ASC -> cliente.nome: ASC
		var orders = pageable.getSort().stream()
			.filter(order -> fieldsMapping.containsKey(order.getProperty()))//remove propriedade inexsitente
			.map(order -> new Sort.Order(order.getDirection(), 
					fieldsMapping.get(order.getProperty())))
			.collect(Collectors.toList());

		//instanciamos o pageable passando o que ja tinhamos, e passamos um sort (lista de orders).
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
				Sort.by(orders));
	}
}
