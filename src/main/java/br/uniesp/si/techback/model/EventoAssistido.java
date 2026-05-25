package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "evento_assistido")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EventoAssistido {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    @Column(name = "assistido_em")
    private LocalDateTime assistidoEm;
}