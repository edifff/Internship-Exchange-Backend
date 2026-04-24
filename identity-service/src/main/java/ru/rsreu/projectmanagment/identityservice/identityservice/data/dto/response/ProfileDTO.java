package ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Role;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String email;
    private Set<Role> role;

    EmployerProfileDTO employerProfileDTO;
    StudentProfileDTO studentProfileDTO;

}
