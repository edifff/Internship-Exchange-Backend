package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateRolesRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management", description = "CRUD пользователей (только ADMIN)")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private UserService userService;

    @GetMapping()
    @Operation(summary = "Получение списка всех пользователей")
    public List<UserDTO> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя по его id")
    public boolean deleteUser(@PathVariable UUID id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по его id")
    public UserDTO getById(@PathVariable UUID id){
        return userService.getById(id);
    }

    @PatchMapping("/{id}/roles")
    @Operation(summary = "Обновление роли пользователя")
    public UserDTO updateRoles(@PathVariable UUID id, @RequestBody UpdateRolesRequest request){
        return userService.updateRole(id, request);
    }

    @DeleteMapping("/{id}/roles")
    @Operation(summary = "Удаление роли пользователя")
    public boolean deleteRoles(@PathVariable UUID id, @RequestBody UpdateRolesRequest request){
        return userService.deleteRole(id, request);
    }
}
