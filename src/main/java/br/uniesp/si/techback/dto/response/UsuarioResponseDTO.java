package br.uniesp.si.techback.dto.response;

import br.uniesp.si.techback.enums.Perfil;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponseDTO(

        UUID id,
        String nomeCompleto,
        LocalDate dataNascimento,
        String email,
        String cpfCnpj,
        Perfil perfil
) {}
