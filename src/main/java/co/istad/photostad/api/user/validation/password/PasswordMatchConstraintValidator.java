package co.istad.photostad.api.user.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstraintValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String password;
    private String confirmedPassword;
    private String message;


    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

        this.password = constraintAnnotation.password();
        this.confirmedPassword = constraintAnnotation.confirmedPassword();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmedPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmedPassword);
        boolean isValid = false;

        if (passwordValue != null) {
            isValid = passwordValue.equals(confirmedPasswordValue);
        }

        if (!isValid) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation();

            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmedPassword)
                    .addConstraintViolation();

        }

        return isValid;

    }


}
