package br.net.digitalzone.algafood.domain.infraestructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.net.digitalzone.algafood.domain.infraestructure.repository.spec.RestauranteSpecs;
import br.net.digitalzone.algafood.domain.model.Restaurante;
import br.net.digitalzone.algafood.domain.repository.RestauranteRepository;
import br.net.digitalzone.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

		// faz o builder com o EntityManager
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		// from Restaurante (O root é o restaurante, a raíz)

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(nome)) {
			// nome like parametro passado
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}

		if (taxaFreteInicial != null) {
			// TaxaInicial deve ser maior ou igual ao parametro passado em taxaFreteInicial
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		if (taxaFreteFinal != null) {
			// TaxaInicial deve ser menor ou igual ao parametro passado em taxaFreteFinal
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}

		// converte um arrayList em um array para a clausula where
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();

	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository
				.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}

	/*
	 * @Override public List<Restaurante> find(String nome, BigDecimal
	 * taxaFreteInicial, BigDecimal taxaFreteFinal) {
	 * 
	 * var jpql = new StringBuilder(); jpql.append("from Restaurante where 1=1 ");
	 * 
	 * var parametros = new HashMap<String, Object>();
	 * 
	 * if (StringUtils.hasLength(nome)) { jpql.append("and nome like :nome ");
	 * parametros.put("nome", "%" + nome + "%"); }
	 * 
	 * if (taxaFreteInicial != null) {
	 * jpql.append("and taxaFrete >= :taxaFreteInicial ");
	 * parametros.put("taxaFreteInicial", taxaFreteInicial); }
	 * 
	 * if (taxaFreteInicial != null) {
	 * jpql.append("and taxaFrete <= :taxaFreteFinal ");
	 * parametros.put("taxaFreteFinal", taxaFreteFinal); }
	 * 
	 * TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(),
	 * Restaurante.class);
	 * 
	 * parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
	 * 
	 * return query.getResultList();
	 * 
	 * // for (String key : parametros.keySet()) { // // query.setParameter(key,
	 * parametros.get(key)); // // System.out.println(key + " - " +
	 * parametros.get(key)); // }
	 * 
	 * // .setParameter("nome","%"+nome+"%") // .setParameter("taxaFreteInicial",
	 * taxaFreteInicial) // .setParameter("taxaFreteFinal", taxaFreteFinal) //
	 * .getResultList();
	 * 
	 * }
	 */

}
