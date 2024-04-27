package co.istad.photostad.api.user;

import com.sun.mail.imap.AppendUID;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SQL;

import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.CharUtils.toChar;

public class UserProvider implements ProviderMethodResolver {
    private final String tblName = "users";

    public String findEmail() {
        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("email=#{email}", "is_deleted=FALSE");

        }}.toString();
    }

    public String findAll(@Param("username") String username) {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            if (!username.isEmpty()) {

                WHERE("username ILIKE CONCAT('%',#{username},'%')", "is_deleted=false");
                OR();
                WHERE("email ILIKE CONCAT('%',#{username},'%')", "is_deleted=false");

            } else {
                WHERE("is_deleted = false");
            }
            ORDER_BY("updated_at DESC");

        }}.toString();

    }

    public String findById() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("id=#{id}", "is_deleted=FALSE");

        }}.toString();

    }

    public String findUserById() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("id=#{id}", "is_deleted=FALSE");

        }}.toString();

    }

    public String deleteByUpdateIsDeletedById() {

        return new SQL() {{

            UPDATE(tblName);
            SET("is_deleted=TRUE");
            WHERE("id=#{id}", "is_deleted=FALSE");

        }}.toString();
    }

    public String deleteById() {

        return new SQL() {{

            DELETE_FROM(tblName);
            WHERE("id=#{id}");

        }}.toString();

    }


    public String update() {

        return new SQL() {{

            UPDATE(tblName);
            SET("username=#{u.username}");
            SET("family_name=#{u.familyName}");
            SET("given_name=#{u.givenName}");
            SET("gender=#{u.gender}");
            SET("dob=#{u.dob}");
            SET("avatar=#{u.avatar.id}");
            SET("phone_number=#{u.phoneNumber}");
            SET("address=#{u.address}");
            SET("biography=#{u.biography}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}");

        }}.toString();

    }

    public String changePassword() {

        return new SQL() {{

            UPDATE(tblName);
            SET("password=#{u.password}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}", "is_deleted=false");

        }}.toString();

    }

    public String createUser() {

        return new SQL() {{

            INSERT_INTO(tblName);
            VALUES("email", "#{u.email}");
            VALUES("uuid", "#{u.uuid}");
            VALUES("password", "#{u.password}");
            VALUES("is_verified", "#{u.isVerified}");
            VALUES("created_at", "#{u.createdAt}");
            VALUES("updated_at", "#{u.updatedAt}");
            VALUES("is_deleted", "FALSE");
            VALUES("family_name", "#{u.familyName}");
            VALUES("given_name", "#{u.givenName}");
            VALUES("username", "#{u.username}");
            VALUES("avatar", "#{u.avatar.id}");

        }}.toString();

    }

    public String createUserRole() {

        return new SQL() {{

            INSERT_INTO("users_roles");
            VALUES("user_id", "#{userId}");
            VALUES("role_id", "#{roleId}");

        }}.toString();

    }

    public String buildFindUserByPasswordSql() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("password=#{password}");

        }}.toString();
    }

    public String updateProfile() {

        return new SQL() {{

            UPDATE(tblName);
            SET("username=#{u.username}");
            SET("family_name=#{u.familyName}");
            SET("given_name=#{u.givenName}");
            SET("gender=#{u.gender}");
            SET("dob=#{u.dob}");
            SET("avatar=#{u.avatar.id}");
            SET("phone_number=#{u.phoneNumber}");
            SET("address=#{u.address}");
            SET("biography=#{u.biography}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}");

        }}.toString();

    }

    public String updateProfileClint() {

        return new SQL() {{

            UPDATE(tblName);
            SET("username=#{u.username}");
            SET("family_name=#{u.familyName}");
            SET("given_name=#{u.givenName}");
            SET("gender=#{u.gender}");
            SET("avatar=#{u.avatar.id}");
            SET("biography=#{u.biography}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}");

        }}.toString();

    }

    public String updateInformationUserProfileClint() {

        return new SQL() {{

            UPDATE(tblName);
            SET("phone_number=#{u.phoneNumber}");
            SET("dob=#{u.dob}");
            SET("address=#{u.address}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}");

        }}.toString();

    }

}
