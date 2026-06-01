package br.uniesp.si.techback.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavoritoResponseDTO(

        UUID usuarioId,
        UUID conteudoId,
        String tituloConteudo,
        LocalDateTime criadoEm

) {}