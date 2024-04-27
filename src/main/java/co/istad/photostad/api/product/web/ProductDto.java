package co.istad.photostad.api.product.web;

import co.istad.photostad.api.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(

        @NotBlank(message = "Name is required!")
        String name,

        @NotBlank(message = "Description is required!")
        String description,

        @NotNull(message = "Price is required!")
        BigDecimal price,

        @NotNull(message = "CreatedBy is required!")
        Integer createdBy
) {
}
