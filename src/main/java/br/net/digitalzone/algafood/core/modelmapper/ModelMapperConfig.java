package br.net.digitalzone.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.net.digitalzone.algafood.api.v1.model.EnderecoDTO;
import br.net.digitalzone.algafood.api.v1.model.input.ItemPedidoInput;
import br.net.digitalzone.algafood.api.v2.model.input.CidadeInputV2;
import br.net.digitalzone.algafood.domain.model.Cidade;
import br.net.digitalzone.algafood.domain.model.Endereco;
import br.net.digitalzone.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//customizando mapeamento de tipos
		//modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
			//.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		//nao atribuir o id da cidade.
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class).addMappings(
				mapper -> mapper.skip(Cidade::setId));
		
		//criamos um tipo de mapeamento de endereço para endereço model
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class,EnderecoDTO.class);
		
		//Para não setar id no itemPedido pois é autoincremento
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class).addMappings(
				mapper -> mapper.skip(ItemPedido::setId));
		
		//adcionamos o mapeamento de endereco para enderecoDTO
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				EnderecoSrc -> EnderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDTODest,valor) -> enderecoDTODest.getCidade().setEstado(valor));
		
		
		return modelMapper;
	}

}
