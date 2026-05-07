package ru.rsreu.projectmanagment.identityservice.identityservice.helper;

import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.SpecialtyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SpecialtyResolver {

    private final SpecialtyRepository repository;

    @Named("mapSpecialties")
    public Set<Specialty> mapSpecialties(List<String> codes) {

        if (codes == null) {
            return Collections.emptySet();
        }

        return codes.stream()
                .map(n -> repository.findByName(n))
                .collect(Collectors.toSet());
    }
}
