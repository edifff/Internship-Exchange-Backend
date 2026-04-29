package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminVacancyModerationService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper mapper;

    public List<VacancyDTO> getPendingVacancy() {
        List<Vacancy> vacancies = vacancyRepository
                .findAllByStatusAndDeletedAtIsNull(Status.PENDING);

        return mapper.toDTO(vacancies);
    }

    public void setStatus(UUID id, String status) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        vacancy.setStatus(Status.valueOf(status.toUpperCase()));

        vacancyRepository.save(vacancy);
    }
}
