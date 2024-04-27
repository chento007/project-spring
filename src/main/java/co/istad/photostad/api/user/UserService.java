package co.istad.photostad.api.user;

import co.istad.photostad.api.user.web.*;
import com.github.pagehelper.PageInfo;

public interface UserService {

    /**
     * select user by email
     *
     * @param email : search by user's email
     * @return UserDto is data to response user
     */
    UserDto selectEmail(String email);

    /**
     * select user with pagination and according to name if name has value it will search
     *
     * @param page  : location page
     * @param limit : size of page
     * @param name  : username
     * @return pagination
     */

    PageInfo<UserDto> selectAll(int page, int limit, String name);

    /**
     * search user by user ID
     *
     * @param id belong to user
     * @return UserDto is data to response user
     */
    UserDto selectById(Integer id);

    Integer deleteUpdateStatusById(Integer id);

    /**
     * use to select all User with pagination
     *
     * @param page  location of pagination
     * @param limit size of data to response
     * @param name  name of user , if have it will search by name .if not it will search normal
     * @return
     */
    PageInfo<User> selectAllUser(int page, int limit, String name);

    /**
     * use change password
     *
     * @param id                user id
     * @param changePasswordDto data require to update
     * @return email after change
     */
    String changePassword(Integer id, ChangePasswordDto changePasswordDto);

}
