package br.net.digitalzone.algafood.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })//Especifica onde a notação pode ser usada.
@Retention(RUNTIME)//Indica que essa notação pode ser lida em runtime. 
//Caso nao tivesse isso o bean validation nao poderia verificar que uma propriedade está notada
@Constraint(validatedBy = { }) //indica que é uma contraint
@PositiveOrZero //tem composição de outra constraint. O efeito final será uma validação @PositiveOrZero
public @interface TaxaFrete {

	@OverridesAttribute(constraint = PositiveOrZero.class, name = "message" )//substituir atributo
	String message() default "{TaxaFrete.invalida}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}
