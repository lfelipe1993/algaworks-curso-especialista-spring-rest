package br.net.digitalzone.algafood.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


//ElementType.TYPE indica que so pode ser usada numa classe e nao em atributos.
@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {
	
	String message() default "descrição obrigatória inválida.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	String ValorField();
	String descricaoField();
	String descricaoObrigatoria();
	
}
