package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PublicVacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final EmployerProfileRepository employerProfileRepository;

    public VacancyDTO get(UUID id) {
        Vacancy vacancy = getVacancy(id);

        return vacancyMapper.toDTO(vacancy);
    }

    public List<VacancyDTO> getAll() {
        List<Vacancy> vacancies = vacancyRepository.findAllByStatusAndDeletedAtIsNull(Status.ACCEPTED);

        return vacancyMapper.toDTO(vacancies);
    }

    public List<VacancyDTO> getAllCompanyVacancy(UUID id) {
        EmployerProfile employerProfile = getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployer(employerProfile);

        return vacancyMapper.toDTO(vacancies);
    }

    public List<VacancyDTO> getAllActivityCompanyVacancy(UUID id) {
        EmployerProfile employerProfile =getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployerAndDeletedAtIsNull(employerProfile);

        return vacancyMapper.toDTO(vacancies);
    }

    public List<VacancyDTO> getAllDeletedCompanyVacancy(UUID id) {
        EmployerProfile employerProfile = getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployerAndDeletedAtIsNotNull(employerProfile);

        return vacancyMapper.toDTO(vacancies);
    }


    private EmployerProfile getEmployer(UUID id){
        return employerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empoyer is null"));
    }

    private Vacancy getVacancy(UUID id){
       return vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not find"));
    }

}
