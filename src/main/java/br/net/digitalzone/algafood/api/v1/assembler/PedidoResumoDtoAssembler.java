package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.PedidoController;
import br.net.digitalzone.algafood.api.v1.model.PedidoResumoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public PedidoResumoDtoAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoDTO);

		if (algaSecurity.podePesquisarPedidos()) {
			pedidoDTO.add(algaLinks.linkToPedidos());
		}

		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		}
		return pedidoDTO;
	}
}
