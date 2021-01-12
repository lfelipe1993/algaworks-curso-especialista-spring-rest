package br.net.digitalzone.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v2.model.input.CidadeInputV2;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	
	//a Ideia é converter um cidadeinput para um cidade.
	//Passamos o cidadeinput e o cidade que queremos atribuir(nao vai instanciar um cidade)
	public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
		
		//faz esse new Cozinha Para nao dar erro no JPA por estar gerenciando a instancia
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
	
}
