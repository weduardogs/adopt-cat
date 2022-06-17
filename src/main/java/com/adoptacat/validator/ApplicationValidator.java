package com.adoptacat.validator;

import com.adoptacat.model.Gato;
import com.adoptacat.model.Postulacion;
import com.adoptacat.model.User;
import com.adoptacat.service.GatoService;
import com.adoptacat.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ApplicationValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
}

    @Override
    public void validate(Object o, Errors errors) {
     Postulacion postulacion = (Postulacion) o;

        if (postulacion.getEdad() <= 0) {
            errors.rejectValue("edad", "NotEmpty.postulacion.edad");
        }

    }
    
}
