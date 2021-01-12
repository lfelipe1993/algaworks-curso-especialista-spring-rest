package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.GrupoInput;
import br.net.digitalzone.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Grupo toDomainObject(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	
	//a Ideia é converter um grupoinput para um restaurante.
	//Passamos o grupoinput e o grupo que queremos atribuir(nao vai instanciar um grupo)
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
	
}
