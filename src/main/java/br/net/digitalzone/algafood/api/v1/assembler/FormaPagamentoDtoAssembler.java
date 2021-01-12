package br.net.digitalzone.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.net.digitalzone.algafood.api.v1.controller.FormaPagamentoController;
import br.net.digitalzone.algafood.api.v1.model.FormaPagamentoDTO;
import br.net.digitalzone.algafood.api.v1.utils.AlgaLinks;
import br.net.digitalzone.algafood.core.security.AlgaSecurity;
import br.net.digitalzone.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDtoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	@Autowired
	private AlgaSecurity algaSecurity;

	public FormaPagamentoDtoAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}

	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {

		FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
		modelMapper.map(formaPagamento, formaPagamentoDTO);

		if (algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}
		return formaPagamentoDTO;
	}

	@Override
	public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		   CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);
		    
		    if (algaSecurity.podeConsultarFormasPagamento()) {
		        collectionModel.add(algaLinks.linkToFormasPagamento());
		    }
		        
		    return collectionModel;
	}

}
