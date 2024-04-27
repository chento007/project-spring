package co.istad.photostad.api.auth.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDto {
    private Integer id;
    private String uuid;
    private String name;
    private String url;
    private String type;

}
