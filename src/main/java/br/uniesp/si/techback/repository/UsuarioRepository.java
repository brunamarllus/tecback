package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository
        extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpfCnpj(String cpfCnpj);

    Page<Usuario> findAllByOrderByNomeCompletoAsc(
            Pageable pageable
    );
}
