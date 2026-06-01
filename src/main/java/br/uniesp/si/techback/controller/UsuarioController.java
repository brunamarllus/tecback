package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.UsuarioRequestDTO;
import br.uniesp.si.techback.dto.response.UsuarioResponseDTO;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public UsuarioResponseDTO criar(
            @RequestBody
            @Valid
            UsuarioRequestDTO dto
    ) {

        return service.criar(dto);
    }

    @GetMapping
    public Page<UsuarioResponseDTO>
    listar(
            @RequestParam(
                    defaultValue = "0"
            ) int page,

            @RequestParam(
                    defaultValue = "10"
            ) int size
    ) {

        return service
                .listarPaginado(
                        page,
                        size
                );
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO
    buscarPorId(
            @PathVariable UUID id
    ) {

        return service
                .buscarPorId(id);
    }
}
