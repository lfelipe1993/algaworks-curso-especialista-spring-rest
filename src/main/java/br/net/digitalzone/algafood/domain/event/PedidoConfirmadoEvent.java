package br.net.digitalzone.algafood.domain.event;

import br.net.digitalzone.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
	
	private Pedido pedido;
}
