package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
@Tag(name = "Planos", description = "Planos de assinatura disponíveis")
public class PlanoController {

    private final PlanoService service;

    @GetMapping
    @Operation(summary = "Lista todos os planos")
    public ResponseEntity<List<PlanoResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listarTodos()
        );
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Busca um plano por código")
    public ResponseEntity<PlanoResponseDTO> buscar(
            @PathVariable String codigo
    ) {

        return ResponseEntity.ok(
                service.buscarPorCodigo(codigo)
        );
    }
}
