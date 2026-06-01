package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.enums.CodigoPlano;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository repository;
    private String CodigoPlano;

    public List<PlanoResponseDTO> listarTodos() {

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public PlanoResponseDTO buscarPorCodigo(
            String codigo
    ) {

        Plano plano =
                repository.findByCodigo(
                                br.uniesp.si.techback.enums.CodigoPlano.valueOf(String.valueOf(br.uniesp.si.techback.enums.CodigoPlano.valueOf(
                                        codigo.toUpperCase()
                                )))
                        )
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Plano não encontrado"
                                )
                        );

        return converter(plano);
    }

    private PlanoResponseDTO converter(
            Plano plano
    ) {

        return new PlanoResponseDTO(
                plano.getCodigo().name(),
                plano.getLimiteDiario(),
                plano.getStreamsSimultaneos()
        );
    }
}
