package co.istad.photostad.api.user;

import co.istad.photostad.api.image.Image;
import co.istad.photostad.api.image.web.ImageDto;
import co.istad.photostad.api.role.Role;
import co.istad.photostad.config.LocalDateTimeDeserializer;
import co.istad.photostad.config.LocalDateTimeSerializer;
import co.istad.photostad.config.TimeStampDeserializer;
import co.istad.photostad.config.TimeStampSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String uuid;
    private String username;
    private String familyName;
    private String givenName;
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String avatarUrl;
    private Image avatar;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String biography;
    @JsonSerialize(using = TimeStampSerializer.class)
    @JsonDeserialize(using = TimeStampDeserializer.class)
    private Timestamp createdAt;
    @JsonSerialize(using = TimeStampSerializer.class)
    @JsonDeserialize(using = TimeStampDeserializer.class)
    private Timestamp loggedInAt;
    @JsonSerialize(using = TimeStampSerializer.class)
    @JsonDeserialize(using = TimeStampDeserializer.class)
    private Timestamp expiredAt;
    @JsonSerialize(using = TimeStampSerializer.class)
    @JsonDeserialize(using = TimeStampDeserializer.class)
    private Timestamp updatedAt;
    private Boolean isVerified;
    private String verifiedCode;
    private Boolean isDeleted;
    private List<Role> roles;
}
