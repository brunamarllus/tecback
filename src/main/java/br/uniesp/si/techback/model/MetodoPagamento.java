package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "metodo_pagamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MetodoPagamento {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String bandeira;

    @Column(nullable = false, length = 4)
    private String ultimos4;

    @Column(name = "mes_exp")
    private Short mesExp;

    @Column(name = "ano_exp")
    private Short anoExp;

    @Column(name = "nome_portador")
    private String nomePortador;

    @Column(name = "token_gateway")
    private String tokenGateway;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}