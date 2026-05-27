package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AdminVacancyModerationService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper mapper;

    @Transactional(readOnly = true)
    public List<VacancyDTO> getPendingVacancy() {
        List<Vacancy> vacancies = vacancyRepository
                .findAllByStatusAndDeletedAtIsNull(Status.PENDING);

        return mapper.toDTO(vacancies);
    }

    public void setStatus(UUID id, String status) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));
        log.info("Moderation: changing vacancy status to {} for id={}", status.toUpperCase(), id);

        vacancy.setStatus(Status.valueOf(status.toUpperCase()));

        vacancyRepository.save(vacancy);
        log.info("Vacancy status updated: id={}, newStatus={}", id, status);
    }
}
