package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.PedidoController;
import br.net.digitalzone.algafood.api.v1.model.PedidoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Pedido;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public PedidoDtoAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {

		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDTO);

		pedidoDTO.add(algaLinks.linkToPedidos());

		if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			if (pedido.podeSerConfirmado()) {
				pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
			if (pedido.podeSerCancelado()) {
				pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
			if (pedido.podeSerEntregue()) {
				pedidoDTO.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
		}
		
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

		pedidoDTO.getFormapagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

		pedidoDTO.getEnderecoEntrega().getCidade()
				.add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

		pedidoDTO.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});

		return pedidoDTO;
	}

}
