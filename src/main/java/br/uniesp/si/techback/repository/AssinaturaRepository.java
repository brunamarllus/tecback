package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.enums.StatusAssinatura;
import br.uniesp.si.techback.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssinaturaRepository
        extends JpaRepository<Assinatura, UUID> {

    List<Assinatura> findByStatus(
            StatusAssinatura status
    );

    @Query("""
            SELECT a.plano.codigo,
                   COUNT(a)
            FROM Assinatura a
            WHERE a.status = 'ATIVA'
            GROUP BY a.plano.codigo
            """)
    List<Object[]> contarAssinaturasAtivas();
}
