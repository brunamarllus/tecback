package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FuncionarioRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "Cargo obrigatório")
        @Size(max = 100)
        String cargo,

        @NotBlank(message = "CEP obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve ter o formato 00000-000")
        String cep

) {}
