package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.model.Conteudo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ConteudoRepository
        extends JpaRepository<Conteudo, UUID> {

    Page<Conteudo> findByGeneroIgnoreCase(
            String genero,
            Pageable pageable
    );

    Page<Conteudo> findByTipo(
            TipoConteudo tipo,
            Pageable pageable
    );

    @Query("""
            SELECT c
            FROM Conteudo c
            WHERE LOWER(c.titulo)
                LIKE LOWER(CONCAT('%', :buscaPalavraChave, '%'))
            OR LOWER(c.sinopse)
                LIKE LOWER(CONCAT('%', :buscaPalavraChave, '%'))
            """)
    Page<Conteudo> buscarPorPalavraChave(
            String buscaPalavraChave,
            Pageable pageable
    );
}
