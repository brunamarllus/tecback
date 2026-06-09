package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.ConteudoRequestDTO;
import br.uniesp.si.techback.dto.response.ConteudoResponseDTO;
import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.mapper.ConteudoMapper;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.repository.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConteudoService {

    private final ConteudoRepository repository;

    public ConteudoResponseDTO criar(
            ConteudoRequestDTO dto
    ) {

        log.info("Criando conteúdo: titulo={} tipo={}", dto.titulo(), dto.tipo());

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

        log.info("Conteúdo criado com sucesso: id={}", conteudo.getId());

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public Page<ConteudoResponseDTO>
    listar(
            TipoConteudo tipo,
            String genero,
            String buscaPalavraChave,
            Pageable pageable
    ) {

        log.info(
                "Listando conteúdos | tipo={} genero={} buscaPalavraChave={} page={} size={}",
                tipo, genero, buscaPalavraChave, pageable.getPageNumber(), pageable.getPageSize()
        );

        Page<Conteudo> conteudos;

        if (buscaPalavraChave != null && !buscaPalavraChave.isBlank()) {

            conteudos =
                    repository
                            .buscarPorPalavraChave(buscaPalavraChave, pageable);

        } else if (genero != null) {

            conteudos =
                    repository
                            .findByGeneroIgnoreCase(genero, pageable);

        } else if (tipo != null) {

            conteudos =
                    repository.findByTipo(tipo, pageable);

        } else {

            conteudos =
                    repository.findAll(pageable);
        }

        log.debug("Total de conteúdos encontrados: {}", conteudos.getTotalElements());

        return conteudos.map(ConteudoMapper::toResponse);
    }

    public ConteudoResponseDTO
    buscarPorId(UUID id) {

        log.info("Buscando conteúdo por id={}", id);

        Conteudo conteudo =
                repository.findById(id)
                        .orElseThrow(() -> {
                            log.warn("Conteúdo não encontrado: id={}", id);
                            return new RecursoNaoEncontradoException(
                                    "Conteúdo não encontrado"
                            );
                        });

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public ConteudoResponseDTO
    atualizar(
            UUID id,
            ConteudoRequestDTO dto
    ) {

        log.info("Atualizando conteúdo: id={}", id);

        Conteudo conteudo =
                repository.findById(id)
                        .orElseThrow(() -> {
                            log.warn("Conteúdo não encontrado para atualização: id={}", id);
                            return new RecursoNaoEncontradoException(
                                    "Conteúdo não encontrado"
                            );
                        });

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

        log.info("Conteúdo atualizado com sucesso: id={}", id);

        return ConteudoMapper
                .toResponse(conteudo);
    }

    public void deletar(UUID id) {

        log.info("Removendo conteúdo: id={}", id);

        repository.deleteById(id);
    }
}
