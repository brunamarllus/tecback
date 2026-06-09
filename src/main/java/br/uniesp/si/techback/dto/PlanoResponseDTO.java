package br.uniesp.si.techback.dto;

import java.util.UUID;

public record PlanoResponseDTO(

        UUID id,
        String codigo,
        Short limiteDiario,
        Short streamsSimultaneos

) {
}
