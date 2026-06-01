package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.UsuarioRequestDTO;
import br.uniesp.si.techback.dto.response.UsuarioResponseDTO;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioResponseDTO criar(
            UsuarioRequestDTO dto
    ) {

        if (repository.existsByEmail(
                dto.email())) {

            throw new RuntimeException(
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

        return UsuarioMapper
                .toResponse(usuario);
    }

    public Page<UsuarioResponseDTO>
    listarPaginado(
            int page,
            int size
    ) {

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

        Usuario usuario =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuário não encontrado"
                                ));

        return UsuarioMapper
                .toResponse(usuario);
    }
}