package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateCompanyReviewRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.CompanyReviewDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.CompanyReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company-reviews")
@RequiredArgsConstructor
@Tag(name = "Company Reviews", description = "Управление отзывами о компаниях")
public class CompanyReviewController {
    private final CompanyReviewService service;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Оставить отзыв о компании")
    public CompanyReviewDTO create(@Valid @RequestBody CreateCompanyReviewRequest request, Authentication auth) {
        return service.createReview(request, auth);
    }

    @GetMapping("/employer/{employerId}")
    @Operation(summary = "Получить все отзывы о компании")
    public List<CompanyReviewDTO> getByEmployer(@PathVariable UUID employerId) {
        return service.getReviewsByEmployer(employerId);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Получить мои отзывы")
    public List<CompanyReviewDTO> getMy(Authentication auth) {
        return service.getMyReviews(auth);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(summary = "Удалить свой отзыв")
    public void delete(@PathVariable UUID id, Authentication auth) {
        service.deleteReview(id, auth);
    }
}