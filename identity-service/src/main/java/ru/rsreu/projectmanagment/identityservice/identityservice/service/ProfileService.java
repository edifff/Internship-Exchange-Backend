package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.EmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateEmployerProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateStudentProfileRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.EmployerProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.StudentProfileDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.EmployerProfileMapper;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.StudentProfileMapper;

import java.time.Year;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileStudentRepository profileStudentRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final StudentProfileMapper studentProfileMapper;
    private final EmployerProfileMapper employerProfileMapper;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final FileEntityRepository fileEntityRepository;


    public ProfileDTO getMyProfil(Authentication auth) {
        User user = getUserFromAuth(auth);
        boolean isStudent =user.hasRole("ROLE_STUDENT");
        boolean isEmployer = user.hasRole("ROLE_EMPLOYER");
        ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder()
                .email(user.getEmail())
                .role(user.getRoles());


        if (isEmployer) {
            EmployerProfile ep = employerProfileRepository.findByUserId(user.getId());

            if (ep != null) {
                builder.employerProfileDTO(
                        employerProfileMapper.toDTO(ep)
                );
            }
        }
        if (isStudent) {
            StudentProfile sp = profileStudentRepository.findByUserId(user.getId());

            if (sp != null) {
                builder.studentProfileDTO(
                        studentProfileMapper.toDTO(sp)
                );
            }
        }

        return  builder.build();
    }

    private User getUserFromAuth(Authentication auth) {
        System.out.println(auth.getPrincipal().getClass());
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                ()->new RuntimeException("Пользователь не найден в базе при получении профиля"));
        return user;
    }

    public StudentProfileDTO updateStudentProfile(Authentication auth, UpdateStudentProfileRequest request) {
        User user = getUserFromAuth(auth);
        StudentProfile studentProfile = profileStudentRepository.findByUserId(user.getId());

        if (request.getResume() != null) {
            Resume resume = resumeRepository.findById(request.getResume())
                    .orElseThrow(() -> new RuntimeException("Resume not found"));
            studentProfile.setResume(resume);
        }

        if (request.getAvatar() != null) {
            FileEntity avatar = fileEntityRepository.findById(request.getAvatar())
                    .orElseThrow(() -> new RuntimeException("File not found"));
            studentProfile.setAvatar(avatar);
        }

        if (request.getFullName() != null) {
            studentProfile.setFullName(request.getFullName());
        }

        if (request.getGraduationYear() != null) {
            studentProfile.setGraduationYear(Year.of(request.getGraduationYear()));
        }

        profileStudentRepository.save(studentProfile);
        return studentProfileMapper.toDTO(studentProfile);
    }

    public EmployerProfileDTO updateEmployerProfile(Authentication auth, UpdateEmployerProfileRequest request) {
        User user = getUserFromAuth(auth);

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(user.getId());

        if (employerProfile == null) {
            employerProfile = EmployerProfile.builder()
                    .user(user)
                    .build();
        }

        if (request.getCompanyName() != null) {
            employerProfile.setCompanyName(request.getCompanyName());
        }

        if (request.getDescription() != null) {
            employerProfile.setDescription(request.getDescription());
        }

        if (request.getWebsiteLink() != null) {
            employerProfile.setWebsiteLink(request.getWebsiteLink());
        }

        if (request.getLogo() != null) {
            FileEntity logo = fileEntityRepository.findById(request.getLogo())
                    .orElseThrow(() -> new RuntimeException("File not found"));
            employerProfile.setLogo(logo);
        }

        employerProfileRepository.save(employerProfile);

        return employerProfileMapper.toDTO(employerProfile);
    }

}