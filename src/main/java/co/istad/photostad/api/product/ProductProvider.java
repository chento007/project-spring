package co.istad.photostad.api.product;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class ProductProvider implements ProviderMethodResolver {


    public String insert() {

        return new SQL() {{

            INSERT_INTO("products");
            VALUES("name", "#{p.name}");
            VALUES("description", "#{p.description}");
            VALUES("price", "#{p.price}");
            VALUES("createdBy", "#{p.createdBy}");
        }}.toString();
    }

    public String findAll() {

        return new SQL() {{

            SELECT("*");
            FROM("products");
            ORDER_BY("created_at DESC");

        }}.toString();

    }
}
