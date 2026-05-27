package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateCompanyReviewRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.CompanyReviewDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.CompanyReview;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.StudentProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.CompanyReviewRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ProfileStudentRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.ConflictException;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.CompanyReviewMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyReviewService {
    private final CompanyReviewRepository repository;
    private final ProfileStudentRepository studentRepo;
    private final EmployerProfileRepository employerRepo;
    private final CompanyReviewMapper mapper;

    @Transactional
    public CompanyReviewDTO createReview(CreateCompanyReviewRequest request, Authentication auth) {
        UUID studentId = ((UserPrincipal) auth.getPrincipal()).getId();

        if (repository.existsByStudentUserIdAndEmployerUserId(studentId, request.getEmployerId())) {
            throw new ConflictException("Вы уже оставили отзыв на эту компанию");
        }

        StudentProfile student = studentRepo.findByUserId(studentId);
        if (student == null) throw new NotFoundException("Профиль студента не найден");

        EmployerProfile employer = employerRepo.findById(request.getEmployerId())
                .orElseThrow(() -> new NotFoundException("Компания не найдена"));

        CompanyReview review = CompanyReview.builder()
                .student(student)
                .employer(employer)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDate.now())
                .build();

        log.info("Review created: student={}, employer={}", studentId, request.getEmployerId());
        return mapper.toDTO(repository.save(review));
    }

    @Transactional(readOnly = true)
    public List<CompanyReviewDTO> getReviewsByEmployer(UUID employerId) {
        return repository.findByEmployerUserId(employerId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CompanyReviewDTO> getMyReviews(Authentication auth) {
        UUID studentId = ((UserPrincipal) auth.getPrincipal()).getId();
        return repository.findByStudentUserId(studentId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public void deleteReview(UUID id, Authentication auth) {
        UUID studentId = ((UserPrincipal) auth.getPrincipal()).getId();
        CompanyReview review = repository.findByIdAndStudentUserId(id, studentId)
                .orElseThrow(() -> new NotFoundException("Отзыв не найден или у вас нет прав на удаление"));
        repository.delete(review);
        log.info("Review deleted: reviewId={}, student={}", id, studentId);
    }
}