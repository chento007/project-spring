package co.istad.photostad.api.user.web;

import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.UserService;
import co.istad.photostad.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/email")
    public BaseRest<?> selectEmail(@RequestParam String email) {

        UserDto userDto = userService.selectEmail(email);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Email has been found success.")
                .data(userDto)
                .build();

    }

    @GetMapping
    public BaseRest<?> selectAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                 @RequestParam(required = false, name = "limit", defaultValue = "20") int limit,
                                 @RequestParam(required = false, name = "name", defaultValue = "") String name
    ) {

        PageInfo<UserDto> userDtoPageInfo = userService.selectAll(page, limit, name.trim());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User has been found success.")
                .data(userDtoPageInfo)
                .build();
    }

    @GetMapping("/{id}")
    public BaseRest<?> selectById(@PathVariable("id") Integer id) {

        UserDto userDto = userService.selectById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User has been found success.")
                .data(userDto)
                .build();

    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteByID(@PathVariable("id") Integer userId) {

        Integer id = userService.deleteUpdateStatusById(userId);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User has been deleted success.")
                .data(id)
                .build();

    }

    @GetMapping("/user-management")
    public BaseRest<?> findAllUserManagement(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                             @RequestParam(required = false, name = "limit", defaultValue = "20") int limit,
                                             @RequestParam(required = false, name = "name", defaultValue = "") String name) {

        PageInfo<User> userPageInfo = userService.selectAllUser(page, limit, name.trim());

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("User has been found success.")
                .data(userPageInfo)
                .build();

    }

    @PutMapping("/{id}/change-password-user")
    public BaseRest<?> changePassword(@PathVariable("id") Integer id, @Valid @RequestBody ChangePasswordDto changePasswordDto) {

        String password = userService.changePassword(id, changePasswordDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Your password has been change success.")
                .data(password)
                .build();
    }
}
