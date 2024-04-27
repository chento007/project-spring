package co.istad.photostad.api.role;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class RoleProvider implements ProviderMethodResolver {

    private final String tblName = "roles";

    public String findAll(@Param("name") String name) {
        return new SQL() {{
            SELECT("*");
            FROM(tblName);
            if (!name.isEmpty()) {
                WHERE("name ILIKE CONCAT('%', #{name}, '%')");
            }
            WHERE("name <> 'GUEST'");
        }}.toString();
    }

    public String findById() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("id = #{id}");

        }}.toString();

    }

    public String update() {

        return new SQL() {{

            UPDATE(tblName);
            SET("name = #{r.name}");
            WHERE("id = #{r.id}");

        }}.toString();

    }
}
