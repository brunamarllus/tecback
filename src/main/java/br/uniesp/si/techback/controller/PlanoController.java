package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.dto.request.PlanoRequestDTO;
import br.uniesp.si.techback.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @Operation(summary = "Cadastra um novo plano")
    public ResponseEntity<PlanoResponseDTO> cadastrar(
            @Valid @RequestBody PlanoRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Deleta um plano por código")
    public ResponseEntity<Void> deletar(
            @PathVariable String codigo
    ) {

        service.deletar(codigo);
        return ResponseEntity.noContent().build();
    }
}
