package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Favorite;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.StudentProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.FavoriteRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ProfileStudentRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final VacancyRepository vacancyRepository;
    private final ProfileStudentRepository studentRepository;
    private final VacancyMapper mapper;

    @Transactional
    public void addToFavorites(UUID vacancyId, Authentication auth) {
        UUID userId = getUserId(auth);

        if (favoriteRepository.existsByStudentUserIdAndVacancyId(userId, vacancyId)) {
            log.debug("Already in favorites | User: {}, Vacancy: {}", userId, vacancyId);
            return;
        }

        log.info("Add to favorites | User: {}, Vacancy: {}", userId, vacancyId);
        StudentProfile student = studentRepository.findByUserId(userId);
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        Favorite favorite = Favorite.builder()
                .student(student)
                .vacancy(vacancy)
                .createdAt(LocalDateTime.now())
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFromFavorites(UUID vacancyId, Authentication auth) {
        UUID userId = getUserId(auth);
        log.info("Remove from favorites | User: {}, Vacancy: {}", userId, vacancyId);

        favoriteRepository.deleteByStudentUserIdAndVacancyId(userId, vacancyId);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> getFavorites(Authentication auth) {
        UUID userId = getUserId(auth);
        log.debug("Get favorites | User: {}", userId);

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