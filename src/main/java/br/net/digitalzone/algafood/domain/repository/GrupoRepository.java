package br.net.digitalzone.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.net.digitalzone.algafood.domain.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	
	
}
