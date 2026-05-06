package ru.rsreu.projectmanagment.identityservice.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.FavoriteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorite Management")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{vacancyId}")
    @Operation(summary = "Добавление в избранное")
    public void add(@PathVariable UUID vacancyId, Authentication auth) {
        favoriteService.addToFavorites(vacancyId, auth);
    }

    @DeleteMapping("/{vacancyId}")
    @Operation(summary = "Удаление из избранного")
    public void remove(@PathVariable UUID vacancyId, Authentication auth) {
        favoriteService.removeFromFavorites(vacancyId, auth);
    }

    @GetMapping
    @Operation(summary = "Получение избранного")
    public List<VacancyDTO> getAll(Authentication auth) {
        return favoriteService.getFavorites(auth);
    }
}
