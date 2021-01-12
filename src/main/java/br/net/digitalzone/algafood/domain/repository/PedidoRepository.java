package br.net.digitalzone.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.net.digitalzone.algafood.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>,
		JpaSpecificationExecutor<Pedido>{
	
	//@Query("from Pedido where codigo=:codigo") Spring Data JPA faz pra gente automaticamente a query
	Optional<Pedido> findByCodigo(String codigo);
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
	
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);

}
