package co.istad.photostad.api.user.validation.user;

import co.istad.photostad.api.user.validation.role.RoleIdConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserIdConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UserIdConstraint {
    String message() default "User ID is not already exits !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
