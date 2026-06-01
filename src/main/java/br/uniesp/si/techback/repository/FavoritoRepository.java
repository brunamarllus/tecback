package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FavoritoRepository
        extends JpaRepository<Favorito, FavoritoId> {

    @Query("""
            SELECT f
            FROM Favorito f
            WHERE f.usuario.id = :usuarioId
            ORDER BY f.criadoEm DESC
            """)
    List<Favorito> listarFavoritosUsuario(
            UUID usuarioId
    );
}
