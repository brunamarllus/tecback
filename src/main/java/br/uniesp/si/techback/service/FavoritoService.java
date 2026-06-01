package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.exception.RecursoNaoEncontradoException;
import br.uniesp.si.techback.model.*;
import br.uniesp.si.techback.repository.ConteudoRepository;
import br.uniesp.si.techback.repository.FavoritoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConteudoRepository conteudoRepository;

    public FavoritoResponseDTO adicionar(
            UUID usuarioId,
            UUID conteudoId
    ) {

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"
                        ));

        Conteudo conteudo = conteudoRepository
                .findById(conteudoId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Conteúdo não encontrado"
                        ));

        LocalDateTime agora = LocalDateTime.now();

        Favorito favorito = Favorito.builder()
                .id(new FavoritoId(usuarioId, conteudoId))
                .usuario(usuario)
                .conteudo(conteudo)
                .criadoEm(agora)
                .build();

        favoritoRepository.save(favorito);

        return new FavoritoResponseDTO(
                usuarioId,
                conteudoId,
                conteudo.getTitulo(),
                agora
        );
    }

    public void remover(
            UUID usuarioId,
            UUID conteudoId
    ) {

        FavoritoId id =
                new FavoritoId(
                        usuarioId,
                        conteudoId
                );

        favoritoRepository.deleteById(id);
    }

    public List<FavoritoResponseDTO>
    listarPorUsuario(UUID usuarioId) {

        return favoritoRepository
                .listarFavoritosUsuario(usuarioId)
                .stream()
                .map(f -> new FavoritoResponseDTO(
                        f.getUsuario().getId(),
                        f.getConteudo().getId(),
                        f.getConteudo().getTitulo(),
                        f.getCriadoEm()
                ))
                .toList();
    }
}
