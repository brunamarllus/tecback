package br.uniesp.si.techback.dto;

public record PlanoResponseDTO(

        String codigo,
        Short limiteDiario,
        Short streamsSimultaneos

) {
}
