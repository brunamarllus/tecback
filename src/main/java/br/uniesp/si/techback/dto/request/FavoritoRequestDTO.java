package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record FavoritoRequestDTO(

        @NotNull UUID usuarioId,
        @NotNull UUID conteudoId

) {}
