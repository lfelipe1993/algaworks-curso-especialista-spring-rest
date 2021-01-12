package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.EstadoController;
import br.net.digitalzone.algafood.api.v1.model.EstadoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Estado;

//Montar um Restaurante DTO
@Component
public class EstadoDtoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public EstadoDtoAssembler() {
		super(EstadoController.class, EstadoDTO.class);
		// TODO Auto-generated constructor stub
	}

	public EstadoDTO toModel(Estado estado) {

		// Cria uma cidade com o id, e a instancia de cidade.
		EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);

		modelMapper.map(estado, estadoDTO);// com isso reduzimos o codigo do self.

		if (algaSecurity.podeConsultarEstados()) {
			estadoDTO.add(algaLinks.linkToEstados("estados"));
		}
		return estadoDTO;
	}

	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
		CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarEstados()) {
			collectionModel.add(algaLinks.linkToEstados());
		}

		return collectionModel;
	}

}
