package br.uniesp.si.techback.model;

import br.uniesp.si.techback.enums.StatusAssinatura;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assinatura")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Assinatura {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura status;

    @Column(name = "iniciada_em")
    private LocalDateTime iniciadaEm;

    @Column(name = "cancelada_em")
    private LocalDateTime canceladaEm;
}