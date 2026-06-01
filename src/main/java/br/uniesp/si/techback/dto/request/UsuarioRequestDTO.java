package br.uniesp.si.techback.dto.request;

import br.uniesp.si.techback.enums.Perfil;
import br.uniesp.si.techback.validation.annotation.CPFouCNPJValido;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        @Size(max = 150)
        String nomeCompleto,

        @NotNull
        LocalDate dataNascimento,

        @NotBlank
        @Email
        @Size(max = 254)
        String email,

        @NotBlank
        @Size(min = 8)
        String senha,

        @CPFouCNPJValido
        String cpfCnpj,

        @NotNull
        Perfil perfil
) {}
