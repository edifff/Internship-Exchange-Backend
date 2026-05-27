package ru.rsreu.projectmanagment.identityservice.identityservice.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateRolesRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.RoleRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.UserRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.UserMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser() {
        log.debug("Admin: get all users");
        return userMapper.toListDTO( userRepository.findAll());
    }

    public boolean deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            log.warn("Admin: delete user failed, not found | Id: {}", id);
            return false;
        }

        userRepository.deleteById(id);
        log.info("Admin: delete user | Id: {}", id);

        return true;
    }

    @Transactional(readOnly = true)
    public UserDTO getById(UUID id) {
        log.debug("Admin: get user | Id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateRole(UUID id, UpdateRolesRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Role role=roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new NotFoundException("Role not found"));

        user.addRole(role);

        userRepository.save(user);

        log.info("Admin: update role | UserId: {}, Role: {}", id, request.getRole());

        return userMapper.toDTO(user);
    }

    @Transactional
    public boolean deleteRole(UUID id, UpdateRolesRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Role role=roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new NotFoundException("Role not found"));

        boolean isDelete=user.deleteRole(role);

        userRepository.save(user);

        log.info("Admin: delete role | UserId: {}, Role: {}", id, request.getRole());

        return isDelete;
    }
}
