package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.MetodoPagamentoRequestDTO;
import br.uniesp.si.techback.dto.response.MetodoPagamentoResponseDTO;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/metodos-pagamento")
@RequiredArgsConstructor
public class MetodoPagamentoController {

    private final MetodoPagamentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetodoPagamentoResponseDTO adicionar(
            @RequestBody
            @Valid
            MetodoPagamentoRequestDTO dto
    ) {

        return service.adicionar(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<MetodoPagamentoResponseDTO> listar(
            @PathVariable UUID usuarioId
    ) {

        return service.listarPorUsuario(usuarioId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @PathVariable UUID id
    ) {

        service.remover(id);
    }
}