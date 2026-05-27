package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.config.VacancySpecificationConfig;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.filter.VacancyFilter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PublicVacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final EmployerProfileRepository employerProfileRepository;
    private final VacancySpecificationConfig vacancySpecificationConfig;

    @Transactional(readOnly = true)
    public VacancyDTO get(UUID id) {
        Vacancy vacancy = getVacancy(id);

        log.debug("Get vacancy | Id: {}", id);
        return vacancyMapper.toDTO(vacancy);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> search(VacancyFilter filter){
        Specification<Vacancy> specification = vacancySpecificationConfig.build(filter);

        List<Vacancy> vacancies = vacancyRepository.findAll(specification);

        log.debug("Search vacancies | Filter: {}", filter);
        return vacancyMapper.toDTO(vacancies);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> getAll() {
        List<Vacancy> vacancies = vacancyRepository.findAllByStatusAndDeletedAtIsNull(Status.ACCEPTED);

        log.debug("Get all accepted vacancies");
        return vacancyMapper.toDTO(vacancies);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> getAllCompanyVacancy(UUID id) {
        EmployerProfile employerProfile = getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployer(employerProfile);
        log.debug("Get company vacancies | EmployerId: {}", id);

        return vacancyMapper.toDTO(vacancies);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> getAllActivityCompanyVacancy(UUID id) {
        EmployerProfile employerProfile =getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployerAndDeletedAtIsNull(employerProfile);

        log.debug("Get active company vacancies | EmployerId: {}", id);

        return vacancyMapper.toDTO(vacancies);
    }

    @Transactional(readOnly = true)
    public List<VacancyDTO> getAllDeletedCompanyVacancy(UUID id) {
        EmployerProfile employerProfile = getEmployer(id);

        List<Vacancy> vacancies = vacancyRepository.findAllByEmployerAndDeletedAtIsNotNull(employerProfile);

        log.debug("Get deleted company vacancies | EmployerId: {}", id);

        return vacancyMapper.toDTO(vacancies);
    }


    private EmployerProfile getEmployer(UUID id){
        return employerProfileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empoyer is null"));
    }

    private Vacancy getVacancy(UUID id){
       return vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy not find"));
    }

}
