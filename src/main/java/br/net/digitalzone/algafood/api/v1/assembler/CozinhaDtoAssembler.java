package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.CozinhaController;
import br.net.digitalzone.algafood.api.v1.model.CozinhaDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Cozinha;

//Montar um Restaurante DTO
@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	@Autowired
	private AlgaSecurity algaSecurity;

	public CozinhaDtoAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {

		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);

		modelMapper.map(cozinha, cozinhaDTO);

		if (algaSecurity.podeConsultarCozinhas()) {
			cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
		}
		
		return cozinhaDTO;
	}

}
