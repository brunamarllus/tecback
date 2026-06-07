package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FilmeDTO;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.mapper.FilmeMapper;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final FilmeMapper filmeMapper;

    public List<Filme> listarOrdenado() {
        return filmeRepository.listarFilmesOrdenados();
    }

    public List<FilmeDTO> listar() {
        log.info("Buscando todos os filmes cadastrados");
        try {
            List<Filme> filmes = filmeRepository.findAll();
            List<FilmeDTO> filmesDTO = filmes.stream()
                    .map(filmeMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de filmes encontrados: {}", filmesDTO.size());
            return filmesDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar filmes: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FilmeDTO buscarPorId(Long id) {
        log.info("Buscando filme pelo ID: {}", id);
        Filme filme = filmeRepository.findById(id)
                .map(filmeEncontrado -> {
                    log.debug("Filme encontrado: ID={}, Título={}", filmeEncontrado.getId(), filmeEncontrado.getTitulo());
                    return filmeEncontrado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Filme não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RecursoNaoEncontradoException(mensagem);
                });
        return filmeMapper.toDTO(filme);
    }

    @Transactional
    public FilmeDTO atualizar(Long id, FilmeDTO filmeDTO) {
        log.info("Atualizando filme ID: {}", id);
        Filme filmeAtualizado = filmeRepository.findById(id)
                .map(filmeExistente -> {
                    log.debug("Dados atuais do filme: {}", filmeExistente);
                    log.debug("Novos dados: {}", filmeDTO);
                    filmeDTO.setId(id);
                    Filme filmeParaAtualizar = filmeMapper.toEntity(filmeDTO);
                    Filme filmeSalvo = filmeRepository.save(filmeParaAtualizar);
                    log.info("Filme ID: {} atualizado com sucesso. Novo título: {}",
                            id, filmeSalvo.getTitulo());
                    return filmeSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: filme não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RecursoNaoEncontradoException(mensagem);
                });
        return filmeMapper.toDTO(filmeAtualizado);
    }

    @Transactional
    public FilmeDTO salvar(FilmeDTO filmeDTO) {
        log.info("Salvando novo filme: {}", filmeDTO.getTitulo());
        try {
            Filme filme = filmeMapper.toEntity(filmeDTO);
            Filme filmeSalvo = filmeRepository.save(filme);
            log.info("Filme salvo com sucesso. ID: {}, Título: {}", filmeSalvo.getId(), filmeSalvo.getTitulo());
            return filmeMapper.toDTO(filmeSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar filme '{}': {}", filmeDTO.getTitulo(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo filme ID: {}", id);
        if (!filmeRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: filme não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RecursoNaoEncontradoException(mensagem);
        }
        try {
            filmeRepository.deleteById(id);
            log.info("Filme ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir filme ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
