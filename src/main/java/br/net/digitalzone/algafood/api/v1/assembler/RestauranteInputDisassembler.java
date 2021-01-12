package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.RestauranteInput;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Cozinha;
import br.net.digitalzone.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	
	//a Ideia é converter um restauranteinput para um restaurante.
	//Passamos o restauranteinput e o restaurante que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		//Para evitar br.net.digitalzone.algafood.domain.model.Cozinha was altered from 1 to 2
		//faz esse new Cozinha Para nao dar erro no JPA por estar gerenciando a instancia
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() !=null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
