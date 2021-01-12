package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.CidadeInput;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Cidade toDomainObject(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	
	//a Ideia é converter um cidadeinput para um cidade.
	//Passamos o cidadeinput e o cidade que queremos atribuir(nao vai instanciar um cidade)
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		
		//faz esse new Cozinha Para nao dar erro no JPA por estar gerenciando a instancia
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
	
}
