package br.net.digitalzone.algafood.domain.repository;

import java.util.Optional;

import br.net.digitalzone.algafood.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
}
