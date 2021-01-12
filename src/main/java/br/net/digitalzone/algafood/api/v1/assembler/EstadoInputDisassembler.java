package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.EstadoInput;
import br.net.digitalzone.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Estado toDomainObject(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	
	//a Ideia é converter um restauranteinput para um restaurante.
	//Passamos o restauranteinput e o restaurante que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
	
}
