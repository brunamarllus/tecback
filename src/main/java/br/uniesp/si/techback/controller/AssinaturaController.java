package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.dto.response.AssinaturaResponseDTO;
import br.uniesp.si.techback.enums.StatusAssinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaService service;

    @PostMapping
    public AssinaturaResponseDTO criar(
            @RequestBody
            @Valid
            AssinaturaRequestDTO dto
    ) {

        return service.criar(dto);
    }

    @PatchMapping("/{id}/cancelar")
    public AssinaturaResponseDTO cancelar(
            @PathVariable UUID id
    ) {

        return service.cancelar(id);
    }

    @GetMapping("/{id}")
    public AssinaturaResponseDTO buscarPorId(
            @PathVariable UUID id
    ) {

        return service.buscarPorId(id);
    }

    @GetMapping
    public List<AssinaturaResponseDTO> listar(
            @RequestParam StatusAssinatura status
    ) {

        return service.listarPorStatus(status);
    }
}
