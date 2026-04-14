package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateRolesRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    public List<User> getAllUser() {
        return null;
    }

    public void deleteUser(UUID id) {
    }

    public UserDTO getById(UUID id) {
        return null;
    }

    public UserDTO updateRole(UUID id, UpdateRolesRequest request) {
        return null;
    }
}
