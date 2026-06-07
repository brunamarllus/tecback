package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.FavoritoRequestDTO;
import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.service.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
@Tag(name = "Favoritos", description = "Conteúdos favoritados pelos usuários")
public class FavoritoController {

    private final FavoritoService service;

    @PostMapping
    @Operation(summary = "Adiciona um conteúdo aos favoritos do usuário")
    public ResponseEntity<FavoritoResponseDTO> adicionar(
            @RequestBody
            @Valid
            FavoritoRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.adicionar(
                        dto.usuarioId(),
                        dto.conteudoId()
                ));
    }

    @DeleteMapping
    @Operation(summary = "Remove um conteúdo dos favoritos do usuário")
    public ResponseEntity<Void> remover(
            @RequestParam UUID usuarioId,
            @RequestParam UUID conteudoId
    ) {

        service.remover(usuarioId, conteudoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista os favoritos de um usuário")
    public ResponseEntity<List<FavoritoResponseDTO>> listar(
            @PathVariable UUID usuarioId
    ) {

        return ResponseEntity.ok(
                service.listarPorUsuario(usuarioId)
        );
    }
}
