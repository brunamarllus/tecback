package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.enums.CodigoPlano;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository repository;

    public List<PlanoResponseDTO> listarTodos() {

        log.info("Listando todos os planos");

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public PlanoResponseDTO buscarPorCodigo(
            String codigo
    ) {

        log.info("Buscando plano por código={}", codigo);

        CodigoPlano codigoPlano;
        try {
            codigoPlano = CodigoPlano.valueOf(codigo.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Código de plano inválido: {}", codigo);
            throw new RecursoNaoEncontradoException("Plano não encontrado");
        }

        Plano plano =
                repository.findByCodigo(codigoPlano)
                        .orElseThrow(() -> {
                            log.warn("Plano não encontrado: codigo={}", codigo);
                            return new RecursoNaoEncontradoException(
                                    "Plano não encontrado"
                            );
                        });

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
