package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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
            if (ep == null) {
                throw new EntityNotFoundException("Employer profile not found for user: " + user.getId());
            }
            builder.employerProfileDTO(
                    employerProfileMapper.toDTO(ep, user)
            );
        }
        if (isStudent) {
            StudentProfile sp = profileStudentRepository.findByUserId(user.getId());
            if (sp == null) {
                throw new EntityNotFoundException("Student profile not found for user: " + user.getId());
            }
            builder.studentProfileDTO(
                    studentProfileMapper.toDTO(sp, user)
            );
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
        return studentProfileMapper.toDTO(studentProfile, user);
    }

    public EmployerProfileDTO updateEmployerProfile(Authentication auth, UpdateEmployerProfileRequest request) {
        User user = getUserFromAuth(auth);

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(user.getId());

        if (employerProfile == null) {
            employerProfile = EmployerProfile.builder()
                    .user(user)
                    .build();
        }

        if (request.getLogo() != null) {
            FileEntity logo = fileEntityRepository.findById(request.getLogo())
                    .orElseThrow(() -> new RuntimeException("File not found"));
            employerProfile.setLogo(logo);
        }

        employerProfile.setCompanyName(request.getCompanyName());
        employerProfile.setDescription(request.getDescription());
        employerProfile.setWebsiteLink(request.getWebsiteLink());

        employerProfileRepository.save(employerProfile);

        return employerProfileMapper.toDTO(employerProfile, user);
    }

}
