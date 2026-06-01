package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.FavoritoRequestDTO;
import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.service.FavoritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService service;

    @PostMapping
    public FavoritoResponseDTO adicionar(
            @RequestBody
            @Valid
            FavoritoRequestDTO dto
    ) {

        return service.adicionar(
                dto.usuarioId(),
                dto.conteudoId()
        );
    }

    @DeleteMapping
    public void remover(
            @RequestParam UUID usuarioId,
            @RequestParam UUID conteudoId
    ) {

        service.remover(
                usuarioId,
                conteudoId
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<FavoritoResponseDTO> listar(
            @PathVariable UUID usuarioId
    ) {

        return service
                .listarPorUsuario(
                        usuarioId
                );
    }
}
