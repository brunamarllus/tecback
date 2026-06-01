package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.service.PlanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService service;

    @GetMapping
    public List<PlanoResponseDTO> listar() {

        return service.listarTodos();
    }

    @GetMapping("/{codigo}")
    public PlanoResponseDTO buscar(
            @PathVariable String codigo
    ) {

        return service.buscarPorCodigo(
                codigo
        );
    }
}
