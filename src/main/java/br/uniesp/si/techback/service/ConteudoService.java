package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.ConteudoRequestDTO;
import br.uniesp.si.techback.dto.response.ConteudoResponseDTO;
import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.mapper.ConteudoMapper;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.repository.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConteudoService {

    private final ConteudoRepository repository;

    public ConteudoResponseDTO criar(
            ConteudoRequestDTO dto
    ) {

        Conteudo conteudo =
                ConteudoMapper.toEntity(dto);

        conteudo.setId(
                UUID.randomUUID()
        );

        conteudo.setCriadoEm(
                LocalDateTime.now()
        );

        conteudo.setAtualizadoEm(
                LocalDateTime.now()
        );

        repository.save(conteudo);

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public List<ConteudoResponseDTO>
    listar(
            TipoConteudo tipo,
            String genero,
            String q
    ) {

        List<Conteudo> conteudos;

        if (q != null && !q.isBlank()) {

            conteudos =
                    repository
                            .buscarPorPalavraChave(q);

        } else if (genero != null) {

            conteudos =
                    repository
                            .findByGeneroIgnoreCaseOrderByTituloAsc(
                                    genero
                            );

        } else if (tipo != null) {

            conteudos =
                    repository.findByTipo(
                            tipo,
                            Sort.by("titulo")
                    );

        } else {

            conteudos =
                    repository
                            .findAllByOrderByTituloAsc();
        }

        return conteudos.stream()
                .map(
                        ConteudoMapper
                                ::toResponse
                )
                .toList();
    }

    public ConteudoResponseDTO
    buscarPorId(UUID id) {

        Conteudo conteudo =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Conteúdo não encontrado"
                                ));

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public ConteudoResponseDTO
    atualizar(
            UUID id,
            ConteudoRequestDTO dto
    ) {

        Conteudo conteudo =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Conteúdo não encontrado"
                                ));

        conteudo.setTitulo(
                dto.titulo()
        );

        conteudo.setTipo(
                dto.tipo()
        );

        conteudo.setAno(
                dto.ano()
        );

        conteudo.setDuracaoMinutos(
                dto.duracaoMinutos()
        );

        conteudo.setRelevancia(
                dto.relevancia()
        );

        conteudo.setSinopse(
                dto.sinopse()
        );

        conteudo.setTrailerUrl(
                dto.trailerUrl()
        );

        conteudo.setGenero(
                dto.genero()
        );

        conteudo.setAtualizadoEm(
                LocalDateTime.now()
        );

        repository.save(conteudo);

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public void deletar(UUID id) {

        repository.deleteById(id);
    }
}
