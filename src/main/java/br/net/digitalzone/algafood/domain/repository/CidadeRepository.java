package br.net.digitalzone.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.digitalzone.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
}
