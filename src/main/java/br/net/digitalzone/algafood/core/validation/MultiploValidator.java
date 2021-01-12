package br.net.digitalzone.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//ConstraintValidator -> interface especificação do bean validation 
//<Annotation, t> annotation indica qual tipo da anotação tratada tratar. O t indica o tipo (Ex Number).
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;
	
	@Override
	//Ele inicializa o validador. Para preparar para as chamadas futuras do metodo isValid
	public void initialize(Multiplo constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		numeroMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if(value != null) {
			//convertando o valor double de value, para instanciar um BigDecimal
			BigDecimal valorDecimal =  BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			
			//remainder devolve o resto
			var resto = valorDecimal.remainder(multiploDecimal);
			
			//comparar um big decimal com outro.
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
		
		return valido;
	}
	

}
