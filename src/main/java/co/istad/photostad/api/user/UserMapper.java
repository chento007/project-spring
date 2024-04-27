package co.istad.photostad.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {




    @Results(
            id = "userResultMap",
            value = {
                    @Result(column = "id", property = "id"),
                    @Result(column = "is_verified", property = "isVerified"),
                    @Result(column = "verified_code", property = "verifiedCode"),
                    @Result(column = "family_name", property = "familyName"),
                    @Result(column = "given_name", property = "givenName"),
                    @Result(column = "phone_number", property = "phoneNumber"),
                    @Result(column = "created_at", property = "createdAt"),
                    @Result(column = "logged_in_at", property = "loggedInAt"),
                    @Result(column = "is_deleted", property = "isDeleted"),
                    @Result(column = "updated_at",property = "updatedAt"),
                    @Result(column = "avatar", property = "avatar", one = @One(select = "findImageById")),
                    @Result(column = "id", property = "roles", many = @Many(select = "findAllRoleByUserId"))
            }
    )
    @SelectProvider(type = UserProvider.class)
    Optional<User> findEmail(String email);


    @ResultMap("userResultMap")
    @SelectProvider(type = UserProvider.class)
    List<User> findAll(String username);


    @ResultMap("userResultMap")
    @SelectProvider(type = UserProvider.class)
    Optional<User> findById(@Param("id") Integer id);


    @UpdateProvider(type = UserProvider.class)
    boolean deleteByUpdateIsDeletedById(Integer id);


    @DeleteProvider(type = UserProvider.class)
    boolean deleteById(@Param("id") Integer id);


    @UpdateProvider(type = UserProvider.class)
    boolean update(@Param("u") User user);


    @InsertProvider(type = UserProvider.class)
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    boolean createUser(@Param("u") User user);


    @ResultMap("userResultMap")
    @SelectProvider(type = UserProvider.class)
    User findUserById(Integer id);


    @InsertProvider(type = UserProvider.class)
    boolean createUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


    @UpdateProvider(type = UserProvider.class)
    boolean changePassword(@Param("u") User user);


    @UpdateProvider(type = UserProvider.class)
    boolean updateProfile(@Param("u") User user);

    @UpdateProvider(type = UserProvider.class)
    boolean updateProfileClint(@Param("u") User user);

    @UpdateProvider(type = UserProvider.class)
    boolean updateInformationUserProfileClint(@Param("u") User user);

    @Select("SELECT EXISTS ( SELECT * FROM users WHERE email = #{email} ) ")
    Boolean exitsByEmail(@Param("email") String email);

    @Select("SELECT EXISTS ( SELECT * FROM roles WHERE id = #{id})")
    Boolean exitByRoleId(@Param("id") Integer id);
}
