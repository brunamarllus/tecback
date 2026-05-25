CREATE TABLE filmes (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo                VARCHAR(100) NOT NULL,
    sinopse               TEXT,
    data_lancamento       DATE,
    genero                VARCHAR(50),
    duracao_minutos       INTEGER,
    classificacao_indicativa VARCHAR(10)
);

CREATE TABLE funcionarios (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(255),
    cargo       VARCHAR(255),
    cep         VARCHAR(255),
    logradouro  VARCHAR(255),
    bairro      VARCHAR(255),
    localidade  VARCHAR(255),
    uf          VARCHAR(255)
);

CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome_completo VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    senha_hash VARCHAR(60) NOT NULL,
    cpf_cnpj VARCHAR(14) UNIQUE,
    perfil VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL,

    CONSTRAINT chk_usuario_perfil
        CHECK (perfil IN ('ADMIN', 'USER'))
);

CREATE TABLE conteudo (
    id UUID PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    ano SMALLINT NOT NULL,
    duracao_minutos SMALLINT NOT NULL,
    relevancia DECIMAL(4,2) NOT NULL,
    sinopse CLOB,
    trailer_url VARCHAR(500),
    genero VARCHAR(50),
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL,

    CONSTRAINT chk_tipo
        CHECK (tipo IN ('FILME', 'SERIE')),

    CONSTRAINT chk_ano
        CHECK (ano BETWEEN 1888 AND 2100),

    CONSTRAINT chk_duracao
        CHECK (duracao_minutos BETWEEN 1 AND 999)
);

CREATE TABLE plano (
    id UUID PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    limite_diario SMALLINT NOT NULL,
    streams_simultaneos SMALLINT NOT NULL
);

CREATE TABLE assinatura (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    plano_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL,
    iniciada_em TIMESTAMP NOT NULL,
    cancelada_em TIMESTAMP,

    CONSTRAINT fk_assinatura_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_assinatura_plano
        FOREIGN KEY (plano_id)
        REFERENCES plano(id)
);

CREATE TABLE metodo_pagamento (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    bandeira VARCHAR(20) NOT NULL,
    ultimos4 CHAR(4) NOT NULL,
    mes_exp SMALLINT NOT NULL,
    ano_exp SMALLINT NOT NULL,
    nome_portador VARCHAR(150) NOT NULL,
    token_gateway VARCHAR(120) NOT NULL,
    criado_em TIMESTAMP NOT NULL,

    CONSTRAINT fk_metodo_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE
);

CREATE TABLE favorito (
    usuario_id UUID NOT NULL,
    conteudo_id UUID NOT NULL,
    criado_em TIMESTAMP NOT NULL,

    PRIMARY KEY (usuario_id, conteudo_id),

    CONSTRAINT fk_favorito_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_favorito_conteudo
        FOREIGN KEY (conteudo_id)
        REFERENCES conteudo(id)
        ON DELETE CASCADE
);

CREATE TABLE evento_assistido (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL,
    conteudo_id UUID NOT NULL,
    assistido_em TIMESTAMP NOT NULL,

    CONSTRAINT fk_evento_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_evento_conteudo
        FOREIGN KEY (conteudo_id)
        REFERENCES conteudo(id)
);