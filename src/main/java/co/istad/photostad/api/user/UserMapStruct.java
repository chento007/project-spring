package co.istad.photostad.api.user;

import co.istad.photostad.api.auth.web.RegisterDto;
import co.istad.photostad.api.user.web.*;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User registerDtoToUser(RegisterDto model);


    UserDto userToUserDto(User model);


    @Mapping(source = "avatar", target = "avatar.id")
    User userModifyDtoToUser(ModifyUserDto model);


    PageInfo<UserDto> userPageInfoToUserDtoPageinfo(PageInfo<User> model);


    @Mapping(source = "avatar", target = "avatar.id")
    User createUserDtoToUser(CreateUserDto model);


    @Mapping(source = "avatar", target = "avatar.id")
    User UpdateProfileAdminDashboardDtoToUser(UpdateProfileDto model);

    @Mapping(source = "avatar", target = "avatar.id")
    User updateProfileClientDtoToUser(UpdateProfileClientDto model);

    User updateInformationClientToUser(UpdateInformationUserDto model);
}
