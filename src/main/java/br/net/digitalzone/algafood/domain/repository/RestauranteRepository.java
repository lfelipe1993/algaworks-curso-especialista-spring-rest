package br.net.digitalzone.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.net.digitalzone.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
	extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
	JpaSpecificationExecutor<Restaurante>{
	
	//Quando a gente faz associação manytoOne ele faz o fetch automaticamente. O que não ocorre no ManyToMany
	//@Query("from Restaurante as r join r.cozinha left join fetch r.formasPagamento")
	@Query("from Restaurante as r join r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> queryBytaxaFreteBetween(BigDecimal taxaInicial,BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);
	//List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long id);
	
	@Query("SELECT case when count(1) > 0 then true else false end from Restaurante rest"
			+ " join rest.responsaveis resp"
			+ "	where rest.id = :restauranteId"
			+ " and resp.id = :usuarioId")
	boolean existsResponsavel(Long restauranteId, Long usuarioId);
	
}
