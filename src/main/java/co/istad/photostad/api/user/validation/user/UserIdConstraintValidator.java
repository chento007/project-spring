package co.istad.photostad.api.user.validation.user;

import co.istad.photostad.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserIdConstraintValidator implements ConstraintValidator<UserIdConstraint, Integer> {
    private final UserMapper userMapper;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        return userMapper.findById(id).isPresent();
    }
}
