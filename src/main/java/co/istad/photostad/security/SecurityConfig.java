package co.istad.photostad.security;

import co.istad.photostad.util.KeyUtil;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsServiceImp;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final KeyUtil keyUtil;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsServiceImp);
        auth.setPasswordEncoder(encoder);
        return auth;

    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        return new JwtAuthenticationConverter();

    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    public JwtAuthenticationProvider jwtAuthenticationProvider() {

        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(cors->cors.disable());
        http.authorizeHttpRequests(auth -> {

            auth.requestMatchers("/resources/**", "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/files/**").permitAll();

            auth.requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("SCOPE_user:read");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("SCOPE_user:write");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("SCOPE_user:update");
            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("SCOPE_user:delete");

            auth.requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAuthority("SCOPE_user:read");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("SCOPE_user:write");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority("SCOPE_user:update");
            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("SCOPE_user:delete");


            auth.requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAuthority("SCOPE_product:read");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("SCOPE_product:write");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority("SCOPE_product:update");
            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("SCOPE_product:delete");


            auth.requestMatchers(HttpMethod.GET, "/api/v1/roles/**").permitAll();
            auth.anyRequest().permitAll();
        });

        http.httpBasic(AbstractHttpConfigurer::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://127.0.0.1:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    @Primary
    public JwtDecoder jwtDecoder()  {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();
    }


    @Bean
    @Primary
    public JwtEncoder jwtEncoder() {

        JWK jwk = new RSAKey.Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();

        return new NimbusJwtEncoder((jwkSelector, context)
                -> jwkSelector.select(new JWKSet(jwk)));
    }


    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    public JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getRefreshTokenPublicKey()).build();
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    public JwtEncoder jwtRefreshTokenEncoder() {

        JWK jwk = new RSAKey.Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .build();

        return new NimbusJwtEncoder((jwkSelector, context)
                -> jwkSelector.select(new JWKSet(jwk)));
    }
}
