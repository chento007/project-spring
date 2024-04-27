package co.istad.photostad.api.image;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class ImageProvider implements ProviderMethodResolver {

    private final String tblName = "images";

    public String insert() {

        return new SQL() {{

            INSERT_INTO(tblName);
            VALUES("uuid", "#{i.uuid}");
            VALUES("name", "#{i.name}");
            VALUES("type", "#{i.type}");

        }}.toString();

    }

    public String findById() {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            WHERE("id = #{id}");

        }}.toString();

    }

    public String deleteById() {

        return new SQL() {{

            DELETE_FROM(tblName);
            WHERE("id = #{id}");

        }}.toString();

    }

    public String updateById() {

        return new SQL() {{

            UPDATE(tblName);
            SET("name=#{i.name}");
            SET("type=#{i.type}");
            WHERE("id=#{i.id}");

        }}.toString();

    }

    public String findAll(@Param("type") String type) {

        return new SQL() {{

            SELECT("*");
            FROM(tblName);
            if (!type.isEmpty()) {
                WHERE("type ILIKE CONCAT('%',#{type},'%')");
            }

        }}.toString();

    }

}
