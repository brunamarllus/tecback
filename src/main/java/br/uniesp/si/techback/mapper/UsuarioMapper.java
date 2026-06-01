package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.request.UsuarioRequestDTO;
import br.uniesp.si.techback.dto.response.UsuarioResponseDTO;
import br.uniesp.si.techback.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioResponseDTO toResponse(
            Usuario usuario
    ) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getCpfCnpj(),
                usuario.getPerfil()
        );
    }

    public static Usuario toEntity(
            UsuarioRequestDTO dto
    ) {

        return Usuario.builder()
                .nomeCompleto(
                        dto.nomeCompleto()
                )
                .dataNascimento(
                        dto.dataNascimento()
                )
                .email(dto.email())
                .cpfCnpj(dto.cpfCnpj())
                .perfil(dto.perfil())
                .build();
    }
}
