INSERT INTO PEDIDO (NOME_USUARIO, TOTAL, VALOR_DESCONTO, VALOR_FRETE, NUM_PARCELA, VALOR_PARCELA, FORMA_PAGAMENTO,
                    STATUS, OBSERVACAO, DATA_PEDIDO, DATA_ENTREGA, DATA_PAGAMENTO, DATA_CANCELAMENTO, DATA_ATUALIZACAO,
                    FK_ENDERECO_ENTREGA, FK_USUARIO, DATA_CONCLUSAO)
VALUES ('Cláudio Araújo', 0.01, 10.00, 20.00, 3, 83.33, '', 'Carrinho', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NULL, NOW(), 1, 2, NULL),

       ('Matheus Munari', 150.00, 15.00, 10.00, 5, 30.00, 'Cartão de Crédito', 'Pendente', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 3, 4, NULL),

       ('Guilherme Santiago', 200.00, 20.00, 15.00, 4, 50.00, 'Boleto', 'Pendente', 'Entrega normal', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NULL, NOW(), 5, 8, NULL),

       ('Kauã Nunes', 300.00, 30.00, 20.00, 6, 50.00, 'Pix', 'Concluído', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NOW(), NOW(), 7, 10, DATEADD(DAY, FLOOR(RAND() * 365), NOW())),

       ('Matheus Kikuti', 250.00, 25.00, 12.00, 3, 83.33, 'Cartão de Débito', 'Pendente de pagamento', 'Entrega normal',
        NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 9, 6, NULL),

       ('Cláudio Araújo', 0.01, 10.00, 20.00, 3, 83.33, '', 'Em rota', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NULL, NOW(), 1, 2, NULL),

       ('Matheus Munari', 150.00, 15.00, 10.00, 5, 30.00, 'Cartão de Crédito', 'Em preparo', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 3, 4, NULL),

       ('Guilherme Santiago', 200.00, 20.00, 15.00, 4, 50.00, 'Boleto', 'Pendente', 'Entrega normal', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NULL, NOW(), 5, 8, NULL),

       ('Kauã Nunes', 300.00, 30.00, 20.00, 6, 50.00, 'Pix', 'Concluído', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NOW(), NOW(), 7, 10, DATEADD(DAY, FLOOR(RAND() * 365), NOW())),

       ('Matheus Kikuti', 250.00, 25.00, 12.00, 3, 83.33, 'Cartão de Débito', 'Concluído', 'Entrega normal', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 9, 6, DATEADD(DAY, FLOOR(RAND() * 365), NOW()));
