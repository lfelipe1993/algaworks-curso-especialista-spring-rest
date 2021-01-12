package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.UsuarioInput;
import br.net.digitalzone.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	
	//a Ideia é converter um usuarioinput para um restaurante.
	//Passamos o usuarioinput e o usuario que queremos atribuir(nao vai instanciar um usuario)
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
}
