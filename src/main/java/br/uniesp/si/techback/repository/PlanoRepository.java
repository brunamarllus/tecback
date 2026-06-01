package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.enums.CodigoPlano;
import br.uniesp.si.techback.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlanoRepository
        extends JpaRepository<Plano, UUID> {

    Optional<Plano> findByCodigo(
            CodigoPlano codigo
    );
}
