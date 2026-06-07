package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneroValidator implements ConstraintValidator<Genero, String> {

    private static final List<String> GENEROS_VALIDOS = Arrays.asList(
            "Ação",
            "Aventura",
            "Comédia",
            "Drama",
            "Terror",
            "Ficção Científica",
            "Romance",
            "Documentário",
            "Animação",
            "Musical",
            "Suspense",
            "Fantasia",
            "Thriller",
            "Crime",
            "Biografia",
            "Guerra",
            "História",
            "Faroeste",
            "Família",
            "Esporte"
    );

    private static final Set<String> GENEROS_NORMALIZADOS = GENEROS_VALIDOS.stream()
            .map(GeneroValidator::normalizar)
            .collect(Collectors.toUnmodifiableSet());

    @Override
    public boolean isValid(String genero, ConstraintValidatorContext context) {
        if (genero == null || genero.isBlank()) {
            return true;
        }

        return GENEROS_NORMALIZADOS.contains(normalizar(genero));
    }

    private static String normalizar(String valor) {
        return Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
