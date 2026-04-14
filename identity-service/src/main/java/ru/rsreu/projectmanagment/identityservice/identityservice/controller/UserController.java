package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateRolesRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private UserService userService;

    @GetMapping()
    public List<UserDTO> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable UUID id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable UUID id){
        return userService.getById(id);
    }

    @PatchMapping("/{id}/roles")
    public UserDTO updateRoles(@PathVariable UUID id, @RequestBody UpdateRolesRequest request){
        return userService.updateRole(id, request);
    }

    @DeleteMapping("/{id}/roles")
    public boolean deleteRoles(@PathVariable UUID id, @RequestBody UpdateRolesRequest request){
        return userService.deleteRole(id, request);
    }
}
