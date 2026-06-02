package br.uniesp.si.techback.dto.response;

import br.uniesp.si.techback.enums.CodigoPlano;
import br.uniesp.si.techback.enums.StatusAssinatura;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssinaturaResponseDTO(

        UUID id,
        UUID usuarioId,
        String nomeUsuario,
        CodigoPlano codigoPlano,
        StatusAssinatura status,
        LocalDateTime iniciadaEm,
        LocalDateTime canceladaEm

) {}
