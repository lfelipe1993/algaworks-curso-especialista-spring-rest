package br.net.digitalzone.algafood.domain.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//precisa carregar o bindindresult, pois quando lançamos a exception precisamos buscar as exceptions
	private BindingResult bindingResult;
}
