package co.istad.photostad.api.auth;

import co.istad.photostad.api.image.Image;
import co.istad.photostad.api.user.Authority;
import co.istad.photostad.api.role.Role;
import co.istad.photostad.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface AuthMapper {

    @InsertProvider(type = AuthProvider.class)
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    boolean register(@Param("u") User user);

    @InsertProvider(type = AuthProvider.class)
    boolean createUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


    @Results(
            id = "authResultMap",
            value = {
                    @Result(column = "id", property = "id"),
                    @Result(column = "is_verified", property = "isVerified"),
                    @Result(column = "verified_code", property = "verifiedCode"),
                    @Result(column = "family_name", property = "familyName"),
                    @Result(column = "given_name", property = "givenName"),
                    @Result(column = "phone_number", property = "phoneNumber"),
                    @Result(column = "created_at", property = "createdAt"),
                    @Result(column = "logged_in_at", property = "loggedInAt"),
                    @Result(column = "expired_at", property = "expiredAt"),
                    @Result(column = "is_deleted", property = "isDeleted"),
                    @Result(column = "id", property = "roles", many = @Many(select = "loadUserRoles")),
                    @Result(column = "avatar", property = "avatar", one = @One(select = "findImageById"))
            }
    )

    @SelectProvider(type = AuthProvider.class)
    Optional<User> findEmail(@Param("email") String email);

    @Select("""
            select * from users where
            email=#{email} AND is_deleted=FALSE AND is_verified=TRUE
            """)
    Optional<User> findEmailWithForgotPassword(@Param("email") String email);

    @Select("SELECT *FROM images WHERE id=#{id}")
    Image findImageById(@Param("id") Integer id);

    @SelectProvider(type = AuthProvider.class)
    @Result(column = "id", property = "authorities", many = @Many(select = "loadUserAuthorities"))
    List<Role> loadUserRoles(@Param("id") Integer id);

    @ResultMap("authResultMap")
    @Select("SELECT * FROM users WHERE email =#{email} AND is_verified=FALSE")
    Optional<User> findEmailWithVerify(String email);

    @ResultMap("authResultMap")
    @Select("SELECT *FROM users WHERE email=#{email} AND is_deleted=FALSE AND is_verified=TRUE")
    Optional<User> loadUserByUsername(@Param("email") String email);


    @SelectProvider(type = AuthProvider.class)
    List<Authority> loadUserAuthorities(@Param("roleId") Integer roleId);


    @UpdateProvider(type = AuthProvider.class)
    boolean updateVerifiedCode(@Param("email") String email, @Param("verifiedCode") String verifiedCode, @Param("expiredAt") Timestamp timestamp);

    @SelectProvider(type = AuthProvider.class)
    @ResultMap("authResultMap")
    Optional<User> selectByEmailAndVerifiedCode(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @UpdateProvider(type = AuthProvider.class)
    @ResultMap("authResultMap")
    void verify(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @UpdateProvider(type = AuthProvider.class)
    void updateLoginAt(@Param("u") User user);


    @SelectProvider(type = AuthProvider.class)
    Optional<User> findVerifiedCode(@Param("verifiedCode") String verifiedCode);


    @Select("SELECT *FROM users WHERE email = #{email} AND is_deleted = FALSE")
    Optional<User> selectUserByEmailToUpdateLoginAt(String email);

    @UpdateProvider(type = AuthProvider.class)
    boolean updateEmail(String email);

    @SelectProvider(type = AuthProvider.class)
    Optional<User> findUserWithForgotPassword(@Param("email") String email);


    @UpdateProvider(type = AuthProvider.class)
    boolean updateVerifiedCodeWithForgotPassword(Integer id);

    @Select("""
            SELECT *FROM users WHERE uuid=#{uuid} AND is_deleted=false
            """)
    Optional<User> selectUserByUUID(String uuid);

    @UpdateProvider(type = AuthProvider.class)
    boolean updateUUID(@Param("u") User user);
}
