package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.FormaPagamentoInput;
import br.net.digitalzone.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	
	//a Ideia é converter um restauranteinput para um restaurante.
	//Passamos o restauranteinput e o restaurante que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
	
}
