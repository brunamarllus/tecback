package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.MetodoPagamentoRequestDTO;
import br.uniesp.si.techback.dto.response.MetodoPagamentoResponseDTO;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public MetodoPagamentoResponseDTO adicionar(
            MetodoPagamentoRequestDTO dto
    ) {

        log.info("Adicionando método de pagamento: usuarioId={} bandeira={}", dto.usuarioId(), dto.bandeira());

        Usuario usuario = usuarioRepository
                .findById(dto.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        ));

        MetodoPagamento metodo = MetodoPagamento.builder()
                .id(UUID.randomUUID())
                .usuario(usuario)
                .bandeira(dto.bandeira())
                .ultimos4(dto.ultimos4())
                .mesExp(dto.mesExp())
                .anoExp(dto.anoExp())
                .nomePortador(dto.nomePortador())
                .tokenGateway(dto.tokenGateway())
                .criadoEm(LocalDateTime.now())
                .build();

        repository.save(metodo);

        log.info("Método de pagamento adicionado com sucesso: id={}", metodo.getId());

        return toResponse(metodo);
    }

    public List<MetodoPagamentoResponseDTO>
    listarPorUsuario(UUID usuarioId) {

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado"
            );
        }

        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void remover(UUID id) {

        log.info("Removendo método de pagamento: id={}", id);

        if (!repository.existsById(id)) {
            log.warn("Método de pagamento não encontrado: id={}", id);
            throw new RecursoNaoEncontradoException(
                    "Método de pagamento não encontrado"
            );
        }

        repository.deleteById(id);
    }

    private MetodoPagamentoResponseDTO toResponse(
            MetodoPagamento metodo
    ) {

        return new MetodoPagamentoResponseDTO(
                metodo.getId(),
                metodo.getUsuario().getId(),
                metodo.getBandeira(),
                metodo.getUltimos4(),
                metodo.getMesExp(),
                metodo.getAnoExp(),
                metodo.getNomePortador(),
                metodo.getCriadoEm()
        );
    }
}
