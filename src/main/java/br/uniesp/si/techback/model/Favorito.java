package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorito")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("conteudoId")
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}