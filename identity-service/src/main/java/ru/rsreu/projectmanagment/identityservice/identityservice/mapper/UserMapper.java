package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.UserDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toListDTO(List<User> user);

    UserDTO toDTO(User user);

}
