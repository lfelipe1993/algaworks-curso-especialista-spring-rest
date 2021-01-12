package br.net.digitalzone.algafood.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.net.digitalzone.algafood.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	@Query("SELECT MAX(dataAtualizacao) from FormaPagamento")
	Optional<OffsetDateTime> getLastUpdate();
	
	@Query("SELECT dataAtualizacao from FormaPagamento WHERE id = :dataPgtId")
	Optional<OffsetDateTime> getSingleLastUpdate(Long dataPgtId);
}