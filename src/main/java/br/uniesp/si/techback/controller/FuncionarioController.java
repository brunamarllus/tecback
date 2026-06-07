package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.FuncionarioRequestDTO;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionários", description = "Cadastro de funcionários com consulta de endereço via ViaCEP")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    @Operation(summary = "Lista todos os funcionários")
    public ResponseEntity<List<Funcionario>> listar() {
        return ResponseEntity.ok(funcionarioService.listar());
    }

    @PostMapping
    @Operation(summary = "Inclui um funcionário (preenche endereço pelo CEP via ViaCEP)")
    public ResponseEntity<Funcionario> incluir(@Valid @RequestBody FuncionarioRequestDTO funcionario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(funcionarioService.incluir(funcionario));
    }
}
