package com.adoptacat.validator;

import com.adoptacat.model.User;
import com.adoptacat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * To provide input-data validation for /registration controller with
 * Spring Validator, we implement org.springframework.validation.Validator.
 * Error codes, e.g. Size.userForm.username, are defined
 * by validation.properties
 *
 * @author raitis
 */

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getId() == null || user.getId() == 0) {
            if (userService.findByUsername(user.getUserName()) != null) {
                errors.rejectValue("userName", "Duplicate.userForm.userName");
            }
        }

        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

    }

}
