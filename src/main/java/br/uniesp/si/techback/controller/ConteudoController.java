package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.ConteudoRequestDTO;
import br.uniesp.si.techback.dto.response.ConteudoResponseDTO;
import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.service.ConteudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/conteudos")
@RequiredArgsConstructor
@Tag(name = "Conteúdos", description = "Catálogo de filmes e séries")
public class ConteudoController {

    private final ConteudoService service;

    @PostMapping
    @Operation(summary = "Cria um novo conteúdo")
    public ResponseEntity<ConteudoResponseDTO> criar(
            @RequestBody
            @Valid
            ConteudoRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista conteúdos com filtros opcionais e paginação")
    public ResponseEntity<Page<ConteudoResponseDTO>> listar(@RequestParam(required = false) TipoConteudo tipo,

                                                            @RequestParam(
                                                                    required = false
                                                            )
                                                            String genero,

                                                            @RequestParam(
                                                                    required = false
                                                            )
                                                            String buscaPalavraChave,

                                                            @RequestParam(
                                                                    defaultValue = "0"
                                                            )
                                                            int page,

                                                            @RequestParam(
                                                                    defaultValue = "10"
                                                            )
                                                            int size
    ) {

        return ResponseEntity.ok(
                service.listar(
                        tipo,
                        genero,
                        buscaPalavraChave,
                        PageRequest.of(page, size, Sort.by("titulo"))
                )
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um conteúdo por ID")
    public ResponseEntity<ConteudoResponseDTO>
    buscarPorId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um conteúdo existente")
    public ResponseEntity<ConteudoResponseDTO>
    atualizar(
            @PathVariable UUID id,

            @RequestBody
            @Valid
            ConteudoRequestDTO dto
    ) {

        return ResponseEntity.ok(
                service.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um conteúdo")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id
    ) {

        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
