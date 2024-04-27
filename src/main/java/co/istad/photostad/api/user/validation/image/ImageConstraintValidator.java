package co.istad.photostad.api.user.validation.image;

import co.istad.photostad.api.image.ImageMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageConstraintValidator implements ConstraintValidator<ImageIdConstraint, Integer> {
    private final ImageMapper imageMapper;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        return imageMapper.isIdExits(id);
    }
}
