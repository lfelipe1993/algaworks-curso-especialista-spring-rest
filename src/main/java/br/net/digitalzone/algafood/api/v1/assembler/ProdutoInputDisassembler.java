package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.ProdutoInput;
import br.net.digitalzone.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Produto toDomainObject(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	
	//a Ideia é converter um produtoinput para um restaurante.
	//Passamos o produtoinput e o produto que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}
	
}
