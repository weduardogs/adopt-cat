package com.adoptacat.validator;

import com.adoptacat.model.Gato;
import com.adoptacat.model.User;
import com.adoptacat.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CatValidator implements Validator {

    @Autowired
    private GatoService gatoService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
}

    @Override
    public void validate(Object o, Errors errors) {
     Gato gato = (Gato) o;

        if (gato.getPeso() <= 0.0) {
            errors.rejectValue("peso", "NotEmpty.gato.peso");
        }

    }
    
}
