package co.istad.photostad.api.auth;

import co.istad.photostad.api.auth.web.*;
import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.UserMapStruct;
import co.istad.photostad.api.user.UserMapper;
import co.istad.photostad.api.user.web.ModifyUserDto;
import co.istad.photostad.api.user.web.UserDto;
import co.istad.photostad.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final AuthMapStruct authMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Value("${spring.mail.username}")
    private String appEmail;
    private final JwtEncoder jwtAccessTokenEncoder;
    private JwtEncoder jwtRefreshTokenEncoder;
    @Value("${file.base-url}")
    private String fileBaseUrl;
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;
    @Autowired
    public void setJwtRefreshTokenEncoder(@Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {

        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;

    }

    @Transactional
    @Override
    public String register(RegisterDto registerDto) {

        User user = authMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(true);
        user.setPassword(encoder.encode(registerDto.password()));
        user.setUuid(UUID.randomUUID().toString());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (authMapper.register(user)) {

            boolean isCheck = false;
            isCheck = authMapper.createUserRole(user.getId(), 2);

            if (!isCheck) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Create user role has been fail."
                );
            } else {
                return registerDto.email();
            }

        }

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Register is fail."
        );

    }

    @Override
    public void verify(String email) {
        User user = authMapper.findEmail(email).orElseThrow(

                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Email with %s is not found.", email)
                )

        );

        int randomNumber = new Random().nextInt(900000) + 100000;
        String verifiedCode = String.valueOf(randomNumber);
        user.setExpiredAt(new Timestamp(System.currentTimeMillis()));
        Instant now = Instant.now();
        Timestamp expiredAt = Timestamp.from(now.plus(2, ChronoUnit.MINUTES));
        user.setExpiredAt(expiredAt);

        if (authMapper.updateVerifiedCode(email, verifiedCode, expiredAt)) {
            user.setVerifiedCode(verifiedCode);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "User can not be verified."
            );

        }

        MailUtil.Meta<?> meta = MailUtil.Meta.builder()
                .to(email)
                .from(appEmail)
                .subject("Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Send mail is fail."
            );
        }
    }

    @Override
    public void checkVerify(String email, String verifiedCode) {


        if (!authMapper.findEmail(email).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Your email %s Search not found.", email)
            );
        }

        if (!authMapper.findVerifiedCode(verifiedCode).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Code verified %s is Invalid try again.", verifiedCode)
            );
        }
        User user = authMapper.selectByEmailAndVerifiedCode(email, verifiedCode).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Email Search not found."
                )
        );

        if (user.getExpiredAt().after(new Timestamp(System.currentTimeMillis()))) {

            if (!user.getIsVerified()) {
                authMapper.verify(email, verifiedCode);
            }

        } else {

            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT,
                    "The access code has expired. Please try to verify again!"
            );

        }
    }

    @Override
    public AuthDto login(LoginDto loginDto) {


        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        authentication = authenticationProvider.authenticate(authentication);
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));


        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> !s.startsWith("ROLE_"))
                .toList();


        JwtClaimsSet accessTokenClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();


        JwtClaimsSet refreshTokenClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        String accessToken = jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(accessTokenClaimsSet)).getTokenValue();

        User user = authMapper.selectUserByEmailToUpdateLoginAt(loginDto.email()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with email %s is not found.", loginDto.email())
                )
        );

        user.setLoggedInAt(new Timestamp(System.currentTimeMillis()));

        String refreshToken = jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(refreshTokenClaimsSet)).getTokenValue();

        authMapper.updateLoginAt(user);

        return new AuthDto(
                "Bearer",
                accessToken,
                refreshToken
        );

    }

    @Override
    public AuthDto refreshToken(TokenDto tokenDto) {

        Authentication authentication = new BearerTokenAuthenticationToken(tokenDto.refreshToken());
        authentication = jwtAuthenticationProvider.authenticate(authentication);
        Instant now = Instant.now();
        Jwt jwt = (Jwt) authentication.getCredentials();

        JwtClaimsSet jwtAccessTokenClaimSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(jwt.getSubject())
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = jwtAccessTokenEncoder.encode(
                JwtEncoderParameters.from(
                        jwtAccessTokenClaimSet
                )
        ).getTokenValue();

        return new AuthDto(
                "Bearer",
                accessToken,
                tokenDto.refreshToken()
        );

    }

    @Override
    public UserDto getProfile(Authentication authentication) {

        User user = authMapper.loadUserByUsername(authentication.getName()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Your profile is unavailable!"));

        return authMapStruct.userToUserDto(user);
    }

    @Override
    public String updateEmail(Authentication authentication, String email) {
        if (authMapper.loadUserByUsername(authentication.getName()).isPresent()) {
            if (authMapper.updateEmail(email)) {
                return email;
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Update email is fail."
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Your profile is unavailable!"
        );
    }

    @Override
    public UserProfileDto getProfileClient(Authentication authentication) {

        User user = authMapper.loadUserByUsername(authentication.getName()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Your profile is unavailable!"
                )
        );

        if (user.getAvatar() != null) {

            if (user.getAvatar().getName() != null) {
                user.setAvatarUrl(fileBaseUrl + user.getAvatar().getName());
            }

        } else {

            user.setAvatarUrl(fileBaseUrl + "photo-user.jpg");

        }

        return authMapStruct.userToUserProfileDto(user);
    }

    @Override
    public void verifyForgotPassword(String email) {

        User user = authMapper.findUserWithForgotPassword(email).orElseThrow(

                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Email with %s is not found.", email)
                )

        );

        int randomNumber = new Random().nextInt(900000) + 100000;
        String verifiedCode = String.valueOf(randomNumber);
        user.setExpiredAt(new Timestamp(System.currentTimeMillis()));
        Instant now = Instant.now();
        Timestamp expiredAt = Timestamp.from(now.plus(2, ChronoUnit.MINUTES));
        user.setExpiredAt(expiredAt);

        if (authMapper.updateVerifiedCode(email, verifiedCode, expiredAt)) {
            user.setVerifiedCode(verifiedCode);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "User can not be verified."
            );

        }

        MailUtil.Meta<?> meta = MailUtil.Meta.builder()
                .to(email)
                .from(appEmail)
                .subject("Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Send mail is fail."
            );
        }
    }

    @Override
    public String resetPassword(ChangePasswordDto changePasswordDto) {

        User user = authMapper.findEmailWithForgotPassword(changePasswordDto.email()).orElseThrow(

                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with  %s not found.", changePasswordDto.email())
                )

        );


        User userVerifiedCode = authMapper.findVerifiedCode(changePasswordDto.verifiedCode()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Please verify email to reset password."
                )
        );

        authMapper.updateVerifiedCodeWithForgotPassword(userVerifiedCode.getId());
        String passwordChange = changePasswordDto.password();
        String newPassword = encoder.encode(passwordChange);
        user.setPassword(newPassword);
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (userMapper.changePassword(user)) {
            return changePasswordDto.password();
        }

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "change password is fail, Try again"
        );
    }


    @Override
    public void checkVerifyWithForgotPassword(String email, String verifiedCode) {

        User userEmailExist = authMapper.findUserWithForgotPassword(email).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Your email %s Search not found.", email)
                )
        );

        User userVerifiedCode = authMapper.findVerifiedCode(verifiedCode).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Code verified %s is Invalid try again.", verifiedCode)
                )
        );

        User user = authMapper.selectByEmailAndVerifiedCode(email, verifiedCode).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Email Search not found."
                )
        );


        if (user.getExpiredAt().after(new Timestamp(System.currentTimeMillis()))) {

            if (!user.getIsVerified()) {
                authMapper.verify(email, verifiedCode);
            }

        } else {

            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT,
                    "The access code has expired. Please try to verify again!"
            );

        }
    }

    @Override
    public UserWithGoogleDto registerWithGoogle(UserWithGoogleDto userWithGoogleDto) {

        User user = authMapStruct.userWithGoogleDtoToUser(userWithGoogleDto);
        String uuid = UUID.randomUUID().toString();
        user.setIsVerified(true);
        String googleSecretKey = "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";
        user.setPassword(encoder.encode(userWithGoogleDto.email() + googleSecretKey));
        user.setUuid(uuid);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (authMapper.register(user)) {

            boolean isCheck = authMapper.createUserRole(user.getId(), 2);

            if (!isCheck) {

                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Create user role has been fail."
                );

            } else {

                return UserWithGoogleDto.builder()
                        .email(userWithGoogleDto.email())
                        .build();
            }

        }

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Register is fail."
        );
    }

    @Override
    public UserEditorDto shareUUID(String uuid) {

        User user = authMapper.selectUserByUUID(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Please login before edit."
                )
        );
        return authMapStruct.userToUserEditorDto(user);
    }

    @Override
    public String generateUUID(String uuid) {

        User user = authMapper.selectUserByUUID(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Please login before edit."
                )
        );

        String newUUID = UUID.randomUUID().toString();
        user.setUuid(newUUID);
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (authMapper.updateUUID(user)) {
            return newUUID;
        }

        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Update is fail."
        );
    }

    @Override
    public boolean findEmail(String email) {
        return userMapper.findEmail(email).isPresent();
    }

    @Override
    public UserDto updateUserById(Integer id, ModifyUserDto modifyUserDto) {

        return null;
    }
}
