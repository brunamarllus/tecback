package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.MetodoPagamentoRequestDTO;
import br.uniesp.si.techback.dto.response.MetodoPagamentoResponseDTO;
import br.uniesp.si.techback.service.MetodoPagamentoService;
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
@RequestMapping("/metodos-pagamento")
@RequiredArgsConstructor
@Tag(name = "Métodos de Pagamento", description = "Métodos de pagamento dos usuários")
public class MetodoPagamentoController {

    private final MetodoPagamentoService service;

    @PostMapping
    @Operation(summary = "Adiciona um método de pagamento")
    public ResponseEntity<MetodoPagamentoResponseDTO> adicionar(
            @RequestBody
            @Valid
            MetodoPagamentoRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.adicionar(dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista os métodos de pagamento de um usuário")
    public ResponseEntity<List<MetodoPagamentoResponseDTO>> listar(
            @PathVariable UUID usuarioId
    ) {

        return ResponseEntity.ok(
                service.listarPorUsuario(usuarioId)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um método de pagamento")
    public ResponseEntity<Void> remover(
            @PathVariable UUID id
    ) {

        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
