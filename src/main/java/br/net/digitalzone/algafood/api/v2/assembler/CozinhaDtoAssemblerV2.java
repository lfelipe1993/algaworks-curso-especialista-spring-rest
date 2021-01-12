package br.net.digitalzone.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v2.controller.CozinhaControllerV2;
import br.net.digitalzone.algafood.api.v2.model.CozinhaDtoV2;
import br.net.digitalzone.algafood.api.v2.utils.AlgaLinksV2;
import br.net.digitalzone.algafood.domain.model.Cozinha;

//Montar um Restaurante DTO
@Component
public class CozinhaDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDtoV2> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	public CozinhaDtoAssemblerV2() {
		super(CozinhaControllerV2.class,CozinhaDtoV2.class);
	}
	
	@Override
	public CozinhaDtoV2 toModel(Cozinha cozinha) {
		
		CozinhaDtoV2 cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		
		modelMapper.map(cozinha,cozinhaDTO);
		
		cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));

		return cozinhaDTO;
	}

}
