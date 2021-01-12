package br.net.digitalzone.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent//dizendo que é um componente spring(que fornece implementação de serializador)
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, 
			SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		
		gen.writeObjectField("content", page.getContent());
		gen.writeNumberField("size", page.getSize());//qtd de paginas
		gen.writeNumberField("totalElements", page.getTotalElements());//total de elementos
		gen.writeNumberField("totalPages", page.getTotalPages());//total de paginas
		gen.writeNumberField("number", page.getNumber());//pagina que estou
		
		gen.writeEndObject();
		
	}

}
