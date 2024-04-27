package co.istad.photostad.security;

import co.istad.photostad.api.auth.AuthMapper;
import co.istad.photostad.api.user.Authority;
import co.istad.photostad.api.role.Role;
import co.istad.photostad.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {

        User user = authMapper.loadUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User is not valid."
                )
        );

        for (Role role : user.getRoles()) {
            for (Authority authority : role.getAuthorities()) {
                System.out.println(authority.getName());
            }
        }

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);
        return customUserDetails;
    }

}
