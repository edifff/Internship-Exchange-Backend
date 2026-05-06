package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Favorite;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.StudentProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.FavoriteRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ProfileStudentRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final VacancyRepository vacancyRepository;
    private final ProfileStudentRepository studentRepository;
    private final VacancyMapper mapper;

    public void addToFavorites(UUID vacancyId, Authentication auth) {
        UUID userId = getUserId(auth);

        if (favoriteRepository.existsByStudentUserIdAndVacancyId(userId, vacancyId)) {
            return;
        }

        StudentProfile student = studentRepository.findByUserId(userId);
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        Favorite favorite = Favorite.builder()
                .student(student)
                .vacancy(vacancy)
                .createdAt(LocalDate.now())
                .build();

        favoriteRepository.save(favorite);
    }

    public void removeFromFavorites(UUID vacancyId, Authentication auth) {
        UUID userId = getUserId(auth);

        favoriteRepository.deleteByStudentUserIdAndVacancyId(userId, vacancyId);
    }

    public List<VacancyDTO> getFavorites(Authentication auth) {
        UUID userId = getUserId(auth);

        return favoriteRepository.findAllByStudentUserId(userId)
                .stream()
                .map(fav -> fav.getVacancy())
                .map(v -> mapToDTO(v))
                .toList();
    }

    private UUID getUserId(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return principal.getId();
    }

    private VacancyDTO mapToDTO(Vacancy vacancy) {
        return mapper.toDTO(vacancy);
    }
}