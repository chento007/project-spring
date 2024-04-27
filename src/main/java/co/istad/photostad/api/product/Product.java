package co.istad.photostad.api.product;

import co.istad.photostad.api.user.User;
import co.istad.photostad.config.TimeStampDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonDeserialize(using = TimeStampDeserializer.class)
    private Timestamp createdAt;
    private User createdBy;  // This maps to the 'created_by' field in the database
}