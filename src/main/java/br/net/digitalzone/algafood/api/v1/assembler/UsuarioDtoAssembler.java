package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.UsuarioController;
import br.net.digitalzone.algafood.api.v1.model.UsuarioDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public UsuarioDtoAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

	public UsuarioDTO toModel(Usuario usuario) {

		// Cria uma cidade com o id, e a instancia de cidade.
		UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);

		modelMapper.map(usuario, usuarioDTO);// com isso reduzimos o codigo do self.

		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			usuarioDTO.add(algaLinks.linkToUsuarios("usuarios"));

			usuarioDTO.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		}
		return usuarioDTO;
	}

	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToUsuarios());
	}

}
