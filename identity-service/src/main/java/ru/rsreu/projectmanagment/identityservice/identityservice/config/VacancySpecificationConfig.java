package ru.rsreu.projectmanagment.identityservice.identityservice.config;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.stereotype.Component;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.filter.VacancyFilter;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.SpecialtyRepository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

@Component
@AllArgsConstructor
public class VacancySpecificationConfig {

    private final SpecialtyRepository specialtyRepository;

    public Specification<Vacancy> build(VacancyFilter filter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            predicates.add(criteriaBuilder.equal(root.get("status"), Status.ACCEPTED));

            if (filter.getTitle() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + filter.getTitle().toLowerCase() + "%"
                ));
            }

            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("city"), filter.getCity()));
            }

            if (filter.getStartedAt() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("endDate"),
                        filter.getStartedAt()
                ));
            }

            if (filter.getEndedAt() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("startDate"),
                        filter.getEndedAt()
                ));
            }

            if (filter.getSpecialtyId() != null) {

                Specialty specialty = specialtyRepository.findById(filter.getSpecialtyId())
                        .orElseThrow(() -> new RuntimeException("Specialty not found"));

                Join<Object, Object> join = root.join("specialties");
                predicates.add(criteriaBuilder.equal(join.get("id"), filter.getSpecialtyId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}