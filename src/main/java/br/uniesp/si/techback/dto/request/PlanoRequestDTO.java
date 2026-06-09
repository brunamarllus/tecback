package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanoRequestDTO(

        @NotBlank
        String codigo,

        @NotNull
        @Min(1)
        Short limiteDiario,

        @NotNull
        @Min(1)
        Short streamsSimultaneos

) {}
