package br.uniesp.si.techback.dto.response;

import br.uniesp.si.techback.enums.TipoConteudo;

import java.math.BigDecimal;
import java.util.UUID;

public record ConteudoResponseDTO(

        UUID id,
        String titulo,
        TipoConteudo tipo,
        Short ano,
        Short duracaoMinutos,
        BigDecimal relevancia,
        String sinopse,
        String trailerUrl,
        String genero
) {}
