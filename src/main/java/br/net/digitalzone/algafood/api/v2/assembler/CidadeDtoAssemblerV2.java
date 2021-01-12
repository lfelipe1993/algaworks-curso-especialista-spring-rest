package br.net.digitalzone.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v2.controller.CidadeControllerV2;
import br.net.digitalzone.algafood.api.v2.model.CidadeDtoV2;
import br.net.digitalzone.algafood.api.v2.utils.AlgaLinksV2;
import br.net.digitalzone.algafood.domain.model.Cidade;

//Montar um Cidade DTO
@Component
public class CidadeDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDtoV2> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;

	public CidadeDtoAssemblerV2() {
		super(CidadeControllerV2.class, CidadeDtoV2.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CidadeDtoV2 toModel(Cidade cidade) {
		
		//Cria uma cidade com o id, e a instancia de cidade.
		CidadeDtoV2 cidadeDTO = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDTO);//com isso reduzimos o codigo do self.
	    
	    cidadeDTO.add(algaLinks.linkToCidades("cidades"));

		return cidadeDTO;
	}

	@Override
	public CollectionModel<CidadeDtoV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToCidades());
	}
}
