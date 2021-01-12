package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.model.input.PedidoInput;
import br.net.digitalzone.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//converte modelo de api para modelo de negocio.
	public Pedido toDomainObject(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	
	//a Ideia é converter um pedidoinput para um restaurante.
	//Passamos o pedidoinput e o pedido que queremos atribuir(nao vai instanciar um restaurante)
	public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);
	}
	
}
