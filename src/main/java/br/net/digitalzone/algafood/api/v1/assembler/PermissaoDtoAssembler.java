package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.GrupoPermissaoController;
import br.net.digitalzone.algafood.api.v1.model.PermissaoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Permissao;

//Montar um Permissao DTO
@Component
public class PermissaoDtoAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO> {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public PermissaoDtoAssembler() {
		super(GrupoPermissaoController.class, PermissaoDTO.class);
	}

	public PermissaoDTO toModel(Permissao permissao) {
		PermissaoDTO permissaoDTO = modelMapper.map(permissao, PermissaoDTO.class);
		return permissaoDTO;
	}

	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		CollectionModel<PermissaoDTO> collectionModel = super.toCollectionModel(entities)
				.add(algaLinks.linkToPermissoes());
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(algaLinks.linkToPermissoes());
		}

		return collectionModel;
	}

}
