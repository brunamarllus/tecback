INSERT INTO plano
(id, codigo, limite_diario, streams_simultaneos)
VALUES
(RANDOM_UUID(), 'BASICO', 5, 1);

INSERT INTO plano
(id, codigo, limite_diario, streams_simultaneos)
VALUES
(RANDOM_UUID(), 'PADRAO', 20, 2);

INSERT INTO plano
(id, codigo, limite_diario, streams_simultaneos)
VALUES
(RANDOM_UUID(), 'PREMIUM', 999, 4);
