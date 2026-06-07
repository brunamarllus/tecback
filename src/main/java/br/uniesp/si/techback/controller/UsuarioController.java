package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.UsuarioRequestDTO;
import br.uniesp.si.techback.dto.response.UsuarioResponseDTO;
import br.uniesp.si.techback.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    public ResponseEntity<UsuarioResponseDTO> criar(
            @RequestBody
            @Valid
            UsuarioRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista usuários paginados")
    public ResponseEntity<Page<UsuarioResponseDTO>>
    listar(
            @RequestParam(
                    defaultValue = "0"
            ) int page,

            @RequestParam(
                    defaultValue = "10"
            ) int size
    ) {

        return ResponseEntity.ok(
                service.listarPaginado(page, size)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UsuarioResponseDTO>
    buscarPorId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }
}
