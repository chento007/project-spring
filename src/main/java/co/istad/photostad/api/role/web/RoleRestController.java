package co.istad.photostad.api.role.web;

import co.istad.photostad.api.role.Role;
import co.istad.photostad.api.role.RoleService;
import co.istad.photostad.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public BaseRest<?> selectAllRole(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                     @RequestParam(required = false, name = "limit", defaultValue = "20") int limit,
                                     @RequestParam(required = false, name = "name", defaultValue = "") String name) {

        PageInfo<RoleDto> resultFindAll = roleService.selectAllRole(page, limit, name);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Roles have been found success.")
                .data(resultFindAll)
                .build();

    }
    @GetMapping("/{id}")
    public BaseRest<?> selectRoleById(@PathVariable("id") Integer id) {

        RoleDto resultFindById=roleService.selectRoleById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Role has been found success.")
                .data(resultFindById)
                .build();

    }
    @PutMapping("/{id}")
    public BaseRest<?> updateRoleById(@PathVariable("id") Integer id, @Valid @RequestBody CreateRoleDto createRoleDto) {

        RoleDto resultFindById=roleService.updateRoleById(id,createRoleDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Role has been update success.")
                .data(resultFindById)
                .build();

    }

    @GetMapping("/all")
    public BaseRest<?> updateRoleById() {

        List<Role> resultFindById=roleService.selectAll();

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message("Role has been found success.")
                .data(resultFindById)
                .build();

    }
}
