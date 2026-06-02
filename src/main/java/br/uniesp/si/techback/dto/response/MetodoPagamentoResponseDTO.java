package br.uniesp.si.techback.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record MetodoPagamentoResponseDTO(

        UUID id,
        UUID usuarioId,
        String bandeira,
        String ultimos4,
        Short mesExp,
        Short anoExp,
        String nomePortador,
        LocalDateTime criadoEm

) {}