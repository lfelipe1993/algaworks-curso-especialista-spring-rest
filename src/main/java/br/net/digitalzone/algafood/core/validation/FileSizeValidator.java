package br.net.digitalzone.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

//ConstraintValidator -> interface especificação do bean validation 
//<Annotation, t> annotation indica qual tipo da anotação tratada tratar. O t indica o tipo (Ex Number).
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
	
	//por meio deste dataSize vamos fazer o parse(500KB -> 500 bytes)
	private DataSize maxSize;
	
	@Override
	//Ele inicializa o validador. Para preparar para as chamadas futuras do metodo isValid
	public void initialize(FileSize constraintAnnotation) {
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}
}
