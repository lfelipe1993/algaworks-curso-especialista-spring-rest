package br.net.digitalzone.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v2.model.input.CozinhaInputV2;
import br.net.digitalzone.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Cozinha toDomainObject(CozinhaInputV2 cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}
	
	
	//a Ideia é converter um restauranteinput para um restaurante.
	//Passamos o restauranteinput e o restaurante que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);
	}
	
}
