package co.istad.photostad.api.auth;

import co.istad.photostad.api.auth.web.*;
import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.web.ModifyUserDto;
import co.istad.photostad.api.user.web.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    UserDto updateUserById(Integer id, ModifyUserDto modifyUserDto);

    /**
     * use to register use
     *
     * @param registerDto use to get data from client such as role , email and password
     * @return email that register
     */
    String register(RegisterDto registerDto);

    /**
     * use to verify with email when user register
     *
     * @param email : email that use verify then send message verify to that email
     */
    void verify(String email);

    /**
     * use check verify when user verify make sure that user email's have in database and verify code
     *
     * @param email        : user's email to make sure it correct
     * @param verifiedCode : user's verifiedCode to make sure it correct
     */
    void checkVerify(String email, String verifiedCode);

    /**
     * use to login
     *
     * @param loginDto require email and password to check with database
     * @return : token and refresh token jwt after login success
     */
    AuthDto login(LoginDto loginDto);

    /**
     * use to get to token and to make our more security by what we set 1h it refresh
     *
     * @param tokenDto :
     * @return : token and refresh token jwt after login success
     */
    AuthDto refreshToken(TokenDto tokenDto);

    /**
     * use to get profile
     *
     * @param authentication is jwt that's we incrip it to gradenail
     * @return data profile user
     */
    UserDto getProfile(Authentication authentication);

    /**
     * use to update email
     *
     * @param authentication is jwt that's we incrip it to gradenail
     * @param email          user email
     * @return email have been update
     */
    String updateEmail(Authentication authentication, String email);

    /**
     * use to get profile user
     *
     * @param authentication is jwt that's we incrip it to gradenail
     * @return data profile user
     */
    UserProfileDto getProfileClient(Authentication authentication);

    /**
     * use to verify forgot password
     *
     * @param email email user
     */
    void verifyForgotPassword(String email);

    /**
     * use to reset password
     *
     * @param changePasswordDto data is require
     * @return change password
     */
    String resetPassword(ChangePasswordDto changePasswordDto);

    /**
     * use to check verify with forgot password
     *
     * @param email        user's email
     * @param verifiedCode is generate code verify
     */
    void checkVerifyWithForgotPassword(String email, String verifiedCode);

    /**
     * use to register with google
     *
     * @param userWithGoogleDto data is require
     * @return have account with google
     */
    UserWithGoogleDto registerWithGoogle(UserWithGoogleDto userWithGoogleDto);

    /**
     * use to share UUID
     *
     * @param uuid is generation id
     * @return UUID
     */
    UserEditorDto shareUUID(String uuid);

    /**
     * use to generate UUID
     *
     * @param uuid is generation id
     * @return UUID
     */
    String generateUUID(String uuid);

    /**
     * use to find email
     *
     * @param email user's email
     * @return email
     */
    boolean findEmail(String email);
}