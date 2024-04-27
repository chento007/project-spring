package co.istad.photostad.api.user.validation.role;

import co.istad.photostad.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, List<Integer>> {
    private final UserMapper userMapper;

    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {

        for (Integer role : roleIds) {

            if (!userMapper.exitByRoleId(role)) {
                return false;
            }

        }

        return true;
    }
}
