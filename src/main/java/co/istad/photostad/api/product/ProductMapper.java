package co.istad.photostad.api.product;

import co.istad.photostad.api.user.User;
import co.istad.photostad.api.user.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ProductMapper {

    @SelectProvider(type = ProductProvider.class)
    List<Product> findAll();


    @InsertProvider(type = ProductProvider.class)
    Product insert(@Param("p") Product product);
}
