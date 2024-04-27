package co.istad.photostad.api.role;

import co.istad.photostad.api.user.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Role implements GrantedAuthority {

    private Integer id;
    private String name;
    private Set<Authority> authorities;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }

}
