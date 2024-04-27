package co.istad.photostad.api.role;

import co.istad.photostad.api.role.web.CreateRoleDto;
import co.istad.photostad.api.role.web.RoleDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * use to select all role
     *
     * @param page  location of pagination
     * @param limit size of data to response
     * @param name  role's name use to search by name if it doesn't have will search by normal
     * @return pagination of RoleDto
     */
    PageInfo<RoleDto> selectAllRole(int page, int limit, String name);

    /**
     * use to select by id
     *
     * @param id to search
     * @return RoleDto is data prepare to response
     */
    RoleDto selectRoleById(Integer id);

    /**
     * use to update role by id
     *
     * @param id            update by id
     * @param createRoleDto data have to update
     * @return RoleDto is data prepare to response
     */
    RoleDto updateRoleById(Integer id, CreateRoleDto createRoleDto);

    /**
     * use to find all role
     *
     * @return list of role
     */
    List<Role> selectAll();
}
