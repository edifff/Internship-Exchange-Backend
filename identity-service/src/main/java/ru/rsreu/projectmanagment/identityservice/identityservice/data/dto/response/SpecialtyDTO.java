package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class SpecialtyDTO {

    private UUID id;

    private String code;

    private String name;

    private boolean isActive;

}
