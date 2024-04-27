package co.istad.photostad.api.auth;

import co.istad.photostad.api.auth.web.*;
import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.web.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapStruct {
    User registerDtoToUser (RegisterDto model);
    LoggedInProfileDto userToLoggedInProfileDto(User user);
    UserDto userToUserDto(User model);
    UserProfileDto userToUserProfileDto(User model);
    User userWithGoogleDtoToUser(UserWithGoogleDto model);
    UserEditorDto userToUserEditorDto(User model);
}
