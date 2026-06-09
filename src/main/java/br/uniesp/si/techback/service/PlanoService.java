package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoResponseDTO;
import br.uniesp.si.techback.dto.request.PlanoRequestDTO;
import br.uniesp.si.techback.enums.CodigoPlano;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.exception.RegraNegocioException;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public PlanoResponseDTO cadastrar(PlanoRequestDTO dto) {

        log.info("Cadastrando plano com código={}", dto.codigo());

        CodigoPlano codigoPlano;
        try {
            codigoPlano = CodigoPlano.valueOf(dto.codigo().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Código de plano inválido: {}", dto.codigo());
            throw new RecursoNaoEncontradoException("Código de plano inválido: " + dto.codigo());
        }

        if (repository.findByCodigo(codigoPlano).isPresent()) {
            log.warn("Plano já existente: codigo={}", codigoPlano);
            throw new RegraNegocioException("Já existe um plano com o código: " + codigoPlano);
        }

        Plano plano = Plano.builder()
                .id(UUID.randomUUID())
                .codigo(codigoPlano)
                .limiteDiario(dto.limiteDiario())
                .streamsSimultaneos(dto.streamsSimultaneos())
                .build();

        return converter(repository.save(plano));
    }

    public void deletar(String codigo) {

        log.info("Deletando plano com código={}", codigo);

        CodigoPlano codigoPlano;
        try {
            codigoPlano = CodigoPlano.valueOf(codigo.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Código de plano inválido: {}", codigo);
            throw new RecursoNaoEncontradoException("Plano não encontrado");
        }

        Plano plano = repository.findByCodigo(codigoPlano)
                .orElseThrow(() -> {
                    log.warn("Plano não encontrado: codigo={}", codigo);
                    return new RecursoNaoEncontradoException("Plano não encontrado");
                });

        repository.delete(plano);
        log.info("Plano com código={} deletado com sucesso", codigo);
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
