package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.UsuarioRequestDTO;
import br.uniesp.si.techback.dto.response.UsuarioResponseDTO;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.exception.RegraNegocioException;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioResponseDTO criar(
            UsuarioRequestDTO dto
    ) {

        log.info("Criando usuário: email={}", dto.email());

        if (repository.existsByEmail(
                dto.email())) {

            log.warn("Email já cadastrado: {}", dto.email());
            throw new RegraNegocioException(
                    "Email já cadastrado"
            );
        }

        Usuario usuario =
                UsuarioMapper.toEntity(dto);

        usuario.setId(UUID.randomUUID());

        usuario.setSenhaHash(
                encoder.encode(
                        dto.senha()
                )
        );

        usuario.setCriadoEm(
                LocalDateTime.now()
        );

        usuario.setAtualizadoEm(
                LocalDateTime.now()
        );

        repository.save(usuario);

        log.info("Usuário criado com sucesso: id={}", usuario.getId());

        return UsuarioMapper
                .toResponse(usuario);
    }

    public Page<UsuarioResponseDTO>
    listarPaginado(
            int page,
            int size
    ) {

        log.info("Listando usuários paginados: page={} size={}", page, size);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                "nomeCompleto"
                        )
                );

        return repository.findAll(
                        pageable
                )
                .map(
                        UsuarioMapper
                                ::toResponse
                );
    }

    public UsuarioResponseDTO
    buscarPorId(UUID id) {

        log.info("Buscando usuário por id={}", id);

        Usuario usuario =
                repository.findById(id)
                        .orElseThrow(() -> {
                            log.warn("Usuário não encontrado: id={}", id);
                            return new RecursoNaoEncontradoException(
                                    "Usuário não encontrado"
                            );
                        });

        return UsuarioMapper
                .toResponse(usuario);
    }
}
