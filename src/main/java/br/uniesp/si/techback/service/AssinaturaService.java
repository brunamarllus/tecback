package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.dto.response.AssinaturaResponseDTO;
import br.uniesp.si.techback.enums.StatusAssinatura;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.exception.RegraNegocioException;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
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
public class AssinaturaService {

    private final AssinaturaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoRepository planoRepository;

    public AssinaturaResponseDTO criar(
            AssinaturaRequestDTO dto
    ) {

        log.info("Criando assinatura: usuarioId={} planoId={}", dto.usuarioId(), dto.planoId());

        Usuario usuario = usuarioRepository
                .findById(dto.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        ));

        Plano plano = planoRepository
                .findById(dto.planoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Plano não encontrado"
                        ));

        boolean jaTemAtiva = repository
                .findByStatus(StatusAssinatura.ATIVA)
                .stream()
                .anyMatch(a ->
                        a.getUsuario().getId()
                                .equals(dto.usuarioId()));

        if (jaTemAtiva) {
            log.warn("Usuário já possui assinatura ativa: usuarioId={}", dto.usuarioId());
            throw new RegraNegocioException(
                    "Usuário já possui uma assinatura ativa"
            );
        }

        Assinatura assinatura = Assinatura.builder()
                .id(UUID.randomUUID())
                .usuario(usuario)
                .plano(plano)
                .status(StatusAssinatura.ATIVA)
                .iniciadaEm(LocalDateTime.now())
                .build();

        repository.save(assinatura);

        log.info("Assinatura criada com sucesso: id={}", assinatura.getId());

        return toResponse(assinatura);
    }

    public AssinaturaResponseDTO cancelar(UUID id) {

        log.info("Cancelando assinatura: id={}", id);

        Assinatura assinatura = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Assinatura não encontrada"
                        ));

        if (assinatura.getStatus() == StatusAssinatura.CANCELADA) {
            log.warn("Assinatura já cancelada: id={}", id);
            throw new RegraNegocioException(
                    "Assinatura já está cancelada"
            );
        }

        assinatura.setStatus(StatusAssinatura.CANCELADA);
        assinatura.setCanceladaEm(LocalDateTime.now());

        repository.save(assinatura);

        log.info("Assinatura cancelada com sucesso: id={}", id);

        return toResponse(assinatura);
    }

    public AssinaturaResponseDTO buscarPorId(UUID id) {

        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Assinatura não encontrada"
                        ));
    }

    public List<AssinaturaResponseDTO>
    listarPorStatus(StatusAssinatura status) {

        return repository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AssinaturaResponseDTO toResponse(
            Assinatura assinatura
    ) {

        return new AssinaturaResponseDTO(
                assinatura.getId(),
                assinatura.getUsuario().getId(),
                assinatura.getUsuario().getNomeCompleto(),
                assinatura.getPlano().getCodigo(),
                assinatura.getStatus(),
                assinatura.getIniciadaEm(),
                assinatura.getCanceladaEm()
        );
    }
}
