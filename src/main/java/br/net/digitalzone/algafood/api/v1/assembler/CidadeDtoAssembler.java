package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.CidadeController;
import br.net.digitalzone.algafood.api.v1.model.CidadeDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Cidade;

//Montar um Cidade DTO
@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public CidadeDtoAssembler() {
		super(CidadeController.class, CidadeDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CidadeDTO toModel(Cidade cidade) {

		// Cria uma cidade com o id, e a instancia de cidade.
		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeDTO);// com isso reduzimos o codigo do self.
		if (algaSecurity.podeConsultarCidades()) {
			cidadeDTO.add(algaLinks.linkToCidades("cidades"));
		}
		if (algaSecurity.podeConsultarEstados()) {
			cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidadeDTO.getEstado().getId()));
		}

		return cidadeDTO;
	}

	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarCidades()) {
			collectionModel.add(algaLinks.linkToCidades());
		}

		return collectionModel;
	}
}
