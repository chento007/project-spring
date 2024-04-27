package co.istad.photostad.api.image;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Image {

    private Integer id;
    private String uuid;
    private String name;
    private String type;

}
