package br.uniesp.si.techback.model;

import br.uniesp.si.techback.enums.CodigoPlano;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "plano")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Plano {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CodigoPlano codigo;

    @Column(name = "limite_diario", nullable = false)
    private Short limiteDiario;

    @Column(name = "streams_simultaneos", nullable = false)
    private Short streamsSimultaneos;
}