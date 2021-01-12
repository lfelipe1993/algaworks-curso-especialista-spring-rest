package br.net.digitalzone.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.digitalzone.algafood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
	
}
