package br.net.digitalzone.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.net.digitalzone.algafood.domain.event.PedidoCanceladoEvent;
import br.net.digitalzone.algafood.domain.model.Pedido;
import br.net.digitalzone.algafood.domain.service.EnvioEmailService;
import br.net.digitalzone.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioEmail;

	//@EventListener // marca o metodo como listener de eventos
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)//Com essa notação nos podemos especificar qual fase especifica 
	//da transacao os eventos sao disparados
	public void aoConfirmarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();

		var mensagem = Mensagem.builder().assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
				.corpo("emails/pedido-cancelado.html")
				.variavel("pedido", pedido)
				.destinario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
}
