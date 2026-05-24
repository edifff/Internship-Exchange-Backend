package ru.rsreu.projectmanagment.identityservice.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.FileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.FileEntity;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(
            target = "downloadUrl",
            expression = "java(\"/api/files/\" + entity.getId())"
    )
    FileDTO toDTO(FileEntity entity);

}
