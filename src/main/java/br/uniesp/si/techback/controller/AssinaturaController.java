package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.dto.response.AssinaturaResponseDTO;
import br.uniesp.si.techback.enums.StatusAssinatura;
import br.uniesp.si.techback.service.AssinaturaService;
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
@RequestMapping("/assinaturas")
@RequiredArgsConstructor
@Tag(name = "Assinaturas", description = "Gestão de assinaturas dos usuários")
public class AssinaturaController {

    private final AssinaturaService service;

    @PostMapping
    @Operation(summary = "Cria uma nova assinatura")
    public ResponseEntity<AssinaturaResponseDTO> criar(
            @RequestBody
            @Valid
            AssinaturaRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancela uma assinatura")
    public ResponseEntity<AssinaturaResponseDTO> cancelar(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.cancelar(id)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma assinatura por ID")
    public ResponseEntity<AssinaturaResponseDTO> buscarPorId(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @GetMapping
    @Operation(summary = "Lista assinaturas por status")
    public ResponseEntity<List<AssinaturaResponseDTO>> listar(
            @RequestParam StatusAssinatura status
    ) {

        return ResponseEntity.ok(
                service.listarPorStatus(status)
        );
    }
}
