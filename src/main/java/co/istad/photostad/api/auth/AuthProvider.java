package co.istad.photostad.api.auth;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class AuthProvider implements ProviderMethodResolver {
    private final String tblName = "users";

    public String updateUUID() {

        return new SQL() {{

            UPDATE(tblName);
            SET("uuid=#{u.uuid}");
            SET("updated_at=#{u.updatedAt}");
            WHERE("id=#{u.id}", "is_deleted=false");

        }}.toString();

    }

    public String register() {

        return new SQL() {{

            INSERT_INTO(tblName);
            VALUES("email", "#{u.email}");
            VALUES("uuid", "#{u.uuid}");
            VALUES("password", "#{u.password}");
            VALUES("is_verified", "#{u.isVerified}");
            VALUES("created_at", "#{u.createdAt}");
            VALUES("updated_at", "#{u.updatedAt}");
            VALUES("is_deleted", "FALSE");

        }}.toString();
    }


    public String createUserRole() {

        return new SQL() {{

            INSERT_INTO("users_roles");
            VALUES("user_id", "#{userId}");
            VALUES("role_id", "#{roleId}");

        }}.toString();

    }


    public String selectByEmailAndVerifiedCode() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("email=#{email}", "verified_code=#{verifiedCode}");

        }}.toString();

    }


    public String verify() {

        return new SQL() {{

            UPDATE(tblName);
            SET("is_verified=TRUE");
            SET("verified_code=NULL");
            SET("is_deleted=FALSE");
            WHERE("email=#{email}", "verified_code=#{verifiedCode}");

        }}.toString();

    }


    public String updateVerifiedCode() {

        return new SQL() {{

            UPDATE(tblName);
            SET("verified_code=#{verifiedCode}");
            SET("expired_at=#{expiredAt}");
            SET("is_deleted=FALSE");
            WHERE("email=#{email}");

        }}.toString();

    }


    public String loadUserAuthorities() {

        return new SQL() {{

            SELECT("a.id,a.name");
            FROM("authorities AS a");
            INNER_JOIN("roles_authorities AS ra ON ra.authority_id=a.id");
            WHERE("ra.role_id=#{roleId}");

        }}.toString();

    }


    public String loadUserRoles() {

        return new SQL() {{

            SELECT("r.id,r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles AS ur ON ur.role_id=r.id");
            WHERE("ur.user_id=#{id}");

        }}.toString();

    }


    public String updateLoginAt() {

        return new SQL() {{

            UPDATE(tblName);
            SET("logged_in_at=#{u.loggedInAt}");
            WHERE("id=#{u.id}");

        }}.toString();

    }


    public String findEmail() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("email=#{email}", "is_deleted=FALSE", "is_verified=FALSE");

        }}.toString();

    }

    public String findVerifiedCode() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("verified_code = #{verifiedCode}");

        }}.toString();

    }

    public String updateEmail() {

        return new SQL() {{

            UPDATE(tblName);
            SET("email=#{email}");
            SET("is_verified=FALSE");
            SET("verified_code=NULL");
            WHERE("email=#{email}", "is_deleted = false");

        }}.toString();

    }

    public String findUserWithForgotPassword() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("email=#{email}", "is_deleted=FALSE", "is_verified=TRUE");

        }}.toString();

    }

    public String updateVerifiedCodeWithForgotPassword() {

        return new SQL() {{

            UPDATE(tblName);
            SET("verified_code=null");
            WHERE("id=#{id}");

        }}.toString();

    }

}
