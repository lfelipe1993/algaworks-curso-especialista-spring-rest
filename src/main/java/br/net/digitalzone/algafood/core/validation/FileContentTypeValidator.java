package br.net.digitalzone.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

//ConstraintValidator -> interface especificação do bean validation 
//<Annotation, t> annotation indica qual tipo da anotação tratada tratar. O t indica o tipo (Ex Number).
public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;
    
    @Override
    public void initialize(FileContentType constraint) {
        this.allowedContentTypes = Arrays.asList(constraint.allowed());
    }
    
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile == null 
                || this.allowedContentTypes.contains(multipartFile.getContentType());
    }
}
