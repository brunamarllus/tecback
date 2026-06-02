package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record MetodoPagamentoRequestDTO(

        @NotNull
        UUID usuarioId,

        @NotBlank
        String bandeira,

        @NotBlank
        @Size(min = 4, max = 4, message = "Deve conter exatamente 4 dígitos")
        String ultimos4,

        @NotNull
        @Min(1) @Max(12)
        Short mesExp,

        @NotNull
        Short anoExp,

        @NotBlank
        String nomePortador,

        @NotBlank
        String tokenGateway

) {}