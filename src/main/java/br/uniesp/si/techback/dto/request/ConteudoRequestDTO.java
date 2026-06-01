package br.uniesp.si.techback.dto.request;

import br.uniesp.si.techback.enums.TipoConteudo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ConteudoRequestDTO(

        @NotBlank
        @Size(max = 200)
        String titulo,

        @NotNull
        TipoConteudo tipo,

        @NotNull
        @Min(1888)
        @Max(2100)
        Short ano,

        @NotNull
        @Min(1)
        @Max(999)
        Short duracaoMinutos,

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("99.99")
        BigDecimal relevancia,

        String sinopse,

        @Size(max = 500)
        String trailerUrl,

        @Size(max = 50)
        String genero
) {}
