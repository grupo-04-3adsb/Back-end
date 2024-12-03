INSERT INTO AVALIACAO (id_avaliacao, titulo, nota_avaliacao, avaliacao_aprovada, descricao, produto_id, usuario_id,
                       data_hora_avaliacao)
VALUES (1, 'Melhor Agenda', 4, true, 'Produto Bom! A agenda é muito útil.', 1, 1,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (2, 'Agenda Perfeita', 5, true, 'Produto excelente! A agenda é linda e muito útil.', 1, 2,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (3, 'Boa Agenda', 3, true, 'Produto Ok, A agenda é útil.', 1, 3, DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (4, 'Não curti', 2, true, 'Produto mais ou menos! A agenda é de bom material.', 1, 4,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (9, 'Muito Bom', 4, true, 'Produto Bom! A agenda é muito útil.', 1, 1,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (10, 'Agenda Muito boa', 5, true, 'Produto excelente! A agenda é linda e muito útil.', 1, 2,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (11, 'Boa Agenda', 4, true, 'Produto Ok, A agenda é útil.', 1, 3, DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (12, 'Curti demais', 5, true, 'Produto muito bom! A agenda é de bom material.', 1, 4,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()));

INSERT INTO AVALIACAO (id_avaliacao, titulo, nota_avaliacao, avaliacao_aprovada, descricao, produto_id, usuario_id,
                       data_hora_avaliacao)
VALUES (5, 'Curti muito', 4, true, 'Produto muito Bom! A agenda é muito legal.', 2, 1,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (6, 'Agenda Perfeita', 5, true, 'Produto excelente! A agenda é perfeita.', 2, 2,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (7, 'Ok', 3, true, 'Produto Ok, A agenda é de boa qualidade.', 2, 3, DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       (8, 'Demorou muito', 2, true, 'O produto é bom, mas demorou muito para chegar', 2, 4,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()));
