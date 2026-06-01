package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.ConteudoRequestDTO;
import br.uniesp.si.techback.dto.response.ConteudoResponseDTO;
import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.service.ConteudoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conteudos")
@RequiredArgsConstructor
public class ConteudoController {

    private final ConteudoService service;

    @PostMapping
    public ConteudoResponseDTO criar(
            @RequestBody
            @Valid
            ConteudoRequestDTO dto
    ) {

        return service.criar(dto);
    }

    @GetMapping
    public List<ConteudoResponseDTO>
    listar(

            @RequestParam(
                    required = false
            )
            TipoConteudo tipo,

            @RequestParam(
                    required = false
            )
            String genero,

            @RequestParam(
                    required = false
            )
            String q
    ) {

        return service.listar(
                tipo,
                genero,
                q
        );
    }

    @GetMapping("/{id}")
    public ConteudoResponseDTO
    buscarPorId(
            @PathVariable UUID id
    ) {

        return service
                .buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ConteudoResponseDTO
    atualizar(
            @PathVariable UUID id,

            @RequestBody
            @Valid
            ConteudoRequestDTO dto
    ) {

        return service.atualizar(
                id,
                dto
        );
    }

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable UUID id
    ) {

        service.deletar(id);
    }
}
