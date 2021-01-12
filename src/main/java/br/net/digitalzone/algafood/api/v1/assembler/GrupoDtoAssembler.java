package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.GrupoController;
import br.net.digitalzone.algafood.api.v1.model.GrupoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Grupo;

//Montar um Restaurante DTO
@Component
public class GrupoDtoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public GrupoDtoAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}

	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoDTO);

		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoDTO.add(algaLinks.linkToGrupos("grupos"));

			grupoDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}
		return grupoDTO;
	}

	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		   CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);
		    
		    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
		        collectionModel.add(algaLinks.linkToGrupos());
		    }
		    
		    return collectionModel;
	}

}
