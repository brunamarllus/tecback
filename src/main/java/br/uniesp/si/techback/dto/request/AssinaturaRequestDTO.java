package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssinaturaRequestDTO(

        @NotNull UUID usuarioId,
        @NotNull UUID planoId

) {}
