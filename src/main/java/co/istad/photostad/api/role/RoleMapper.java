package co.istad.photostad.api.role;

import co.istad.photostad.api.user.Authority;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface RoleMapper {

    @SelectProvider(type = RoleProvider.class)
    List<Role> findAll(@Param("name") String name);

    @SelectProvider(type = RoleProvider.class)
    Optional<Role> findById(@Param("id") Integer id);

    @UpdateProvider(type = RoleProvider.class)
    boolean update(@Param("r") Role role);


    @Select("SELECT EXISTS ( SELECT * FROM roles WHERE id = #{id} ) ")
    boolean isIdExist(@Param("id") Integer id);


    @Select("select * from roles")
    @Results(
            id = "resultRoleMap",
            value = {
                    @Result(column = "id", property = "authorities", many = @Many(select = "selectAuthorities"))
            }
    )
    List<Role> findAllRole();

    @Select("""
            select a.* from roles_authorities as ra INNER JOIN
            authorities a on a.id = ra.authority_id
            where ra.role_id=#{id}
            """)
    List<Authority> selectAuthorities(@Param("id") Integer id);
}
