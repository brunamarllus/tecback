package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.enums.TipoConteudo;
import br.uniesp.si.techback.model.Conteudo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConteudoRepository
        extends JpaRepository<Conteudo, UUID> {

    List<Conteudo> findAllByOrderByTituloAsc();

    List<Conteudo> findByGeneroIgnoreCaseOrderByTituloAsc(
            String genero
    );

    List<Conteudo> findByTipo(
            TipoConteudo tipo,
            Sort sort
    );

    List<Conteudo> findTop10ByOrderByRelevanciaDesc();

    List<Conteudo> findByAnoGreaterThanOrderByAnoDesc(
            Short ano
    );

    List<Conteudo> findByTrailerUrlIsNotNull();

    @Query("""
            SELECT c
            FROM Conteudo c
            WHERE LOWER(c.titulo)
                LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(c.sinopse)
                LIKE LOWER(CONCAT('%', :q, '%'))
            ORDER BY c.relevancia DESC
            """)
    List<Conteudo> buscarPorPalavraChave(
            String q
    );
}
