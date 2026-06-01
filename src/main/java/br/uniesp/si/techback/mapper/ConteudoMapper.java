package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.request.ConteudoRequestDTO;
import br.uniesp.si.techback.dto.response.ConteudoResponseDTO;
import br.uniesp.si.techback.model.Conteudo;

public class ConteudoMapper {

    private ConteudoMapper() {}

    public static Conteudo toEntity(
            ConteudoRequestDTO dto
    ) {

        return Conteudo.builder()
                .titulo(dto.titulo())
                .tipo(dto.tipo())
                .ano(dto.ano())
                .duracaoMinutos(
                        dto.duracaoMinutos()
                )
                .relevancia(
                        dto.relevancia()
                )
                .sinopse(dto.sinopse())
                .trailerUrl(
                        dto.trailerUrl()
                )
                .genero(dto.genero())
                .build();
    }

    public static ConteudoResponseDTO
    toResponse(Conteudo conteudo) {

        return new ConteudoResponseDTO(
                conteudo.getId(),
                conteudo.getTitulo(),
                conteudo.getTipo(),
                conteudo.getAno(),
                conteudo.getDuracaoMinutos(),
                conteudo.getRelevancia(),
                conteudo.getSinopse(),
                conteudo.getTrailerUrl(),
                conteudo.getGenero()
        );
    }
}
