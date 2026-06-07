package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FilmeDTO;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Filmes", description = "CRUD de filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping("/ordenado")
    @Operation(summary = "Lista filmes ordenados por título")
    public ResponseEntity<List<Filme>> listarOrdenado() {
        log.info("Listando todos os filmes ordenados");
        return ResponseEntity.ok(filmeService.listarOrdenado());
    }

    @GetMapping
    @Operation(summary = "Lista todos os filmes")
    public ResponseEntity<List<FilmeDTO>> listar() {
        log.info("Listando todos os filmes");
        List<FilmeDTO> filmes = filmeService.listar();
        log.debug("Total de filmes encontrados: {}", filmes.size());
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um filme por ID")
    public ResponseEntity<FilmeDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando filme com ID: {}", id);
        return ResponseEntity.ok(filmeService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo filme")
    public ResponseEntity<FilmeDTO> criar(@Valid @RequestBody FilmeDTO filmeDTO) {
        log.info("Recebida requisição para criar novo filme: {}", filmeDTO.getTitulo());
        FilmeDTO filmeSalvo = filmeService.salvar(filmeDTO);
        log.info("Filme criado com sucesso. ID: {}, Título: {}", filmeSalvo.getId(), filmeSalvo.getTitulo());
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um filme existente")
    public ResponseEntity<FilmeDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FilmeDTO filmeDTO) {
        log.info("Atualizando filme com ID {}: {}", id, filmeDTO);
        return ResponseEntity.ok(filmeService.atualizar(id, filmeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um filme")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo filme com ID: {}", id);
        filmeService.excluir(id);
        log.debug("Filme com ID {} excluído com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}
