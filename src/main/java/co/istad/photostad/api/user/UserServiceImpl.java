package co.istad.photostad.api.user;


import co.istad.photostad.api.user.web.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;

    @Override
    public UserDto selectEmail(String email) {

        User user = userMapper.findEmail(email).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with email = %s is not found.", email)
                )
        );

        return userMapStruct.userToUserDto(user);
    }

    @Override
    public PageInfo<UserDto> selectAll(int page, int limit, String name) {

        PageInfo<User> userPageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                () -> userMapper.findAll(name)
        );

        if (!name.isEmpty() && userMapper.findAll(name).isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with name %s is not found.", name)
            );

        }

        return userMapStruct.userPageInfoToUserDtoPageinfo(userPageInfo);
    }

    @Override
    public UserDto selectById(Integer id) {
        User user = userMapper.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %s id not found.", id)
                )
        );
        return userMapStruct.userToUserDto(user);
    }

    @Override
    public Integer deleteUpdateStatusById(Integer id) {

        if (userMapper.findById(id).isPresent()) {

            if (userMapper.deleteByUpdateIsDeletedById(id)) {
                return id;
            }

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Delete user with %d is fail.", id)
            );

        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("User with id %d is not found.", id)
        );

    }


    @Override
    public PageInfo<User> selectAllUser(int page, int limit, String name) {

        PageInfo<User> userPageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                () -> userMapper.findAll(name)
        );

        if (!name.isEmpty() && userMapper.findAll(name).isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User with name %s is not found.", name)
            );

        }

        return userPageInfo;

    }

    @Override
    public String changePassword(Integer id, ChangePasswordDto changePasswordDto) {

        User user = userMapper.findById(id).orElseThrow(

                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %s id not found.", id)
                )

        );

        String cryptPassword = user.getPassword();
        String currentPassword = changePasswordDto.oldPassword();

        if (BCrypt.checkpw(currentPassword, cryptPassword)) {

            String newPassword = encoder.encode(changePasswordDto.newPassword());
            user.setId(id);
            user.setPassword(newPassword);
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            if (userMapper.changePassword(user)) {
                return changePasswordDto.newPassword();
            }

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "change password is fail, Try again"
            );

        } else {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Your old password is invalid, Try again."
            );

        }
    }

}
