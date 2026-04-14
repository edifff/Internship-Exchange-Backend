package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.reqest.UpdateRolesRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RoleRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.UserRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.UserMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUser() {
        return userMapper.toListDTO( userRepository.findAll());
    }

    public boolean deleteUser(UUID id) {
        return userRepository.delete(id);
    }

    public UserDTO getById(UUID id) {
       User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public UserDTO updateRole(UUID id, UpdateRolesRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role=roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.addRole(role);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public boolean deleteRole(UUID id, UpdateRolesRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role=roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isDelete=user.deleteRole(role);
        userRepository.save(user);
        return isDelete;
    }
}
