INSERT INTO categoria (nome_categoria)
VALUES
    ('Papelaria e Escritório'),
    ('Festas e Eventos'),
    ('Adesivos'),
    ('Personalização e Brindes'),
    ('Artesanato e Materiais Artísticos'),
    ('Artigos para Datas Comemorativas');

INSERT INTO subcategoria (nome_subcategoria, descricao_subcategoria, fk_categoria)
VALUES
    ('Agendas e Cadernos', 'Cadernos e agendas personalizadas e decoradas', 1),
    ('Canecas', 'Canecas decorativas e personalizadas', 3),
    ('Decoração para Festas', 'Decoração', 2),
    ('Camisetas Temáticas', 'Camisetas com estampas personalizadas e de personagens', 3),
    ('Decoração de Mesa para Festas', 'Itens decorativos para mesas de festas', 2),
    ('Brindes Corporativos', 'Itens promocionais personalizados para empresas', 3),
    ('Material para Scrapbook e Pintura', 'Material para criação de scrapbook e pintura', 4),
    ('Decoração Natalina', 'Itens de decoração para o Natal', 5),
    ('Brinquedos Educativos', 'Brinquedos que estimulam o aprendizado', 6);

INSERT INTO material (id_material, nome_material, preco_unitario, estoque)
VALUES (1, 'Papel', 0.20, 5),
       (2, 'Cartolina', 0.50, 10),
       (3, 'Tecido', 1.00, 3),
       (4, 'Fita', 0.75, 4),
       (5, 'Plástico', 0.30, 2),
       (6, 'Caneta', 1.50, 20),
       (7, 'Lápis', 0.80, 15),
       (8, 'Caderno', 2.50, 8),
       (9, 'Giz de cera', 0.90, 12),
       (10, 'Borrachas', 0.60, 25),
       (11, 'Marcador permanente', 1.20, 18),
       (12, 'Lápis de cor', 1.10, 22),
       (13, 'Tesoura', 3.00, 7),
       (14, 'Cola', 1.40, 15),
       (15, 'Fita adesiva', 0.90, 11),
       (16, 'Clip', 0.05, 100),
       (17, 'Grampeador', 4.00, 5),
       (18, 'Pasta arquivo', 1.80, 9),
       (19, 'Régua', 0.60, 20),
       (20, 'Caderno brochura', 2.00, 14),
       (21, 'Caderno espiral', 3.50, 10),
       (22, 'Caneta marca-texto', 1.75, 16),
       (23, 'Pincel', 1.00, 8),
       (24, 'Tinta guache', 2.00, 6),
       (25, 'Papel cartão', 0.40, 12),
       (26, 'Corda', 0.25, 30),
       (27, 'Caixa de som', 15.00, 2),
       (28, 'Baterias', 2.50, 20),
       (29, 'Rolo de fita crepe', 1.00, 15),
       (30, 'Capa de chuva', 5.00, 10),
       (31, 'Protetor auricular', 2.00, 10);

INSERT INTO produto (nome, preco_venda, descricao, dimensao, desconto, margem_lucro, sku, url_imagem_principal, personalizavel, personalizacao_obrigatoria, fk_categoria, fk_subcategoria, id_img_drive, produto_Ativo)
VALUES
    -- Produtos de Papelaria
    ('Agenda Minnie', 34.90, 'Agenda decorada com a personagem Minnie.', 'A5', 0.10, 0.30, 'AGENDA-MINNIE', 'https://ascriativasloja.com.br/wp-content/uploads/2023/01/1-12.jpg', true, true, 1, 1, '1OosCxJEq0zdIUuuzpNhzRafG3N6eEyG5', true),
    ('Agenda Mickey', 34.90, 'Agenda decorada com a personagem Mickey.', 'A5', 0.10, 0.30, 'AGENDA-MICKEY', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTh9GrMNq4LrRs6X1Vi4vko2drhPqjBDDwfGVaAaIHiTS2_Dq6TLN96sDZLr4MmBXGYGYg&usqp=CAU', true, true, 1, 1, '1bXl8JQW_FQ8i9-OeMcC_2KYss5_3TeWB', true),
    ('Caderno One Piece', 29.90, 'Caderno com capa de One Piece.', 'A5', 0.05, 0.30, 'CADERNO-ONEPIECE', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiV1TKjZCc9HeLbdhECyNToe6Mk_JntIj9WQ&s', true, true, 1, 1, '1yI3d4MMQfRuXY85iw2ZKXxTC2aqh-70', true),

    -- Canecas
    ('Caneca Kratos', 29.90, 'Caneca com o tema Kratos.', '350ml', 0.10, 0.30, 'CANECA-KRATOS', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB3AXRDyuNngaF58r6ZdNGE4b3D8Br2XVGEQ&s', false, false, 2, 2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Caneca Playstation', 29.90, 'Caneca com o tema Playstation.', '350ml', 0.10, 0.30, 'CANECA-PLAYSTATION', 'https://cdn.awsli.com.br/600x450/1225/1225697/produto/130152295/9ee6196526.jpg', false, false, 2, 2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Caneca Gamer', 29.90, 'Caneca com tema gamer.', '350ml', 0.10, 0.30, 'CANECA-GAMER', 'https://images.tcdn.com.br/img/img_prod/723087/caneca_gamer_i_love_you_3653_1_20201214005540.jpg', false, false, 2, 2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Caneca Java', 29.90, 'Caneca com tema Java.', '350ml', 0.10, 0.30, 'CANECA-JAVA', 'https://cdn.awsli.com.br/608/608801/produto/25906868/caneca-coffee-java-ee4fc9fb.jpg', false, false, 2, 2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),

    -- Camisetas
    ('Camisa Angry Birds', 49.90, 'Camisa personalizada com Angry Birds.', 'M', 0.10, 0.30, 'CAMISA-ANGRYBIRDS', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDzqMgb38UYNyTDTsD_E-Mc6L1Gc5PVvDESw&s', true, true, 2, 3, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Camisa Pokemon', 49.90, 'Camisa personalizada com Pikachu.', 'M', 0.10, 0.30, 'CAMISA-POKEMON', 'https://cdn.dooca.store/292/products/camiseta-pokemon-pikachu-choque-do-trovao-aberta.jpg?v=1585147881', true, true, 2, 3, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),

    -- Decoração
    ('Topo de Bolo Vingadores', 49.90, 'Topo de bolo com tema Vingadores.', '30x20cm', 0.15, 0.25, 'TOPO-BOLO-VINGADORES', 'https://cdn.awsli.com.br/600x1000/761/761999/produto/163707609ce03d02cb1.jpg', false, false, 2, 4, '1bJ4H3RLbY8w2I8Gth7_yTVPrbNYgDnv', true),
    ('Topo de Bolo Barbie', 49.90, 'Topo de bolo com tema Barbie.', '30x20cm', 0.15, 0.25, 'TOPO-BOLO-BARBIE', 'https://img.elo7.com.br/product/685x685/44E707C/topo-de-bolo-barbie-decoracao-barbie.jpg', false, false, 2, 4, '1bJ4H3RLbY8w2I8Gth7_yTVPrbNYgDnv', true),

    -- Adesivos
    ('Adesivo Naruto', 9.90, 'Adesivo com tema Naruto.', '10x10cm', 0.05, 0.20, 'ADESIVO-NARUTO', 'https://http2.mlstatic.com/D_NQ_NP_958149-MLB54876142860_042023-O.webp', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo Dev', 9.90, 'Adesivo com tema Dev.', '10x10cm', 0.05, 0.20, 'ADESIVO-DEV', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkk2ZgCo8-uC1N7nayU5CSl1V4EDI9UuJByrR7mJZUqncpTtKtA5s6htzV-wXcOFagMdU&usqp=CAU', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo Star Wars', 9.90, 'Adesivo com tema Star Wars.', '10x10cm', 0.05, 0.20, 'ADESIVO-STARWARS', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7k3xPJfKnfXu-LUqTbPPTJ6Qs1f2hbnGvaw&s', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo Harry Potter', 9.90, 'Adesivo com tema Harry Potter.', '10x10cm', 0.05, 0.20, 'ADESIVO-HARRYPOTTER', 'https://images.tcdn.com.br/img/img_prod/1083971/adesivos_harry_potter_kit_com_48un_6cm_brindes_51_1_901e122a6c63cff652d4b020c3fa4715.jpg', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo de Música', 9.90, 'Adesivo com tema de música.', '10x10cm', 0.05, 0.20, 'ADESIVO-MUSICA', 'https://http2.mlstatic.com/D_NQ_NP_828482-MLB72737668006_112023-O.webp', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo Retro', 9.90, 'Adesivo com tema retro de jogos.', '10x10cm', 0.05, 0.20, 'ADESIVO-RETRO', 'https://http2.mlstatic.com/D_NQ_NP_702242-MLB77858802843_072024-O.webp', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Adesivo de Cidades', 9.90, 'Adesivo com silhuetas de cidades famosas.', '10x10cm', 0.05, 0.20, 'ADESIVO-CIDADES', 'https://http2.mlstatic.com/D_NQ_NP_948189-MLB70119768954_062023-O.webp', false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),

    -- Brindes
    ('Brinde Corporativo', 12.90, 'Brinde personalizado para eventos corporativos.', '10x10cm', 0.10, 0.25, 'BRINDE-001', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSy9wYDYpAdtUKZwRv1m3kMZE0Wszg6XEI2DQ&s', true, true, 1, 5, '1jW2nqf9hZUyJ3p7Qah4D2azP-HM7sG5', true),

-- Materiais de Escritório
    ('Calendário Empresarial', 19.90, 'Calendário para empresas.', 'N/A', 0.05, 0.20, 'CALENDARIO-EMPRESARIAL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQF8eyt-9h1u1qiLnDr6VvENbOQWxWw0vF6g&s', false, false, 1, 5, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),
    ('Caneta Empresarial', 12.90, 'Caneta personalizada para empresas.', 'N/A', 0.05, 0.20, 'CANETA-EMPRESARIAL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgorUHkv--OP-M2dQx5Mqgv2xkpd8yftQHMA&s', false, false, 1, 5, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true);

INSERT INTO imagens_produto (url_img_adicional, fk_produto, id_img_drive)
VALUES
    -- Agenda Minnie
    ('https://down-br.img.susercontent.com/file/br-11134207-7r98o-ll12v9cj8igic1', 1, '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
    ('https://images.tcdn.com.br/img/img_prod/1090591/agenda_datada_2022_minnie_vermelha_1_807_1_9fbda09577108ffeeeefbbadcb2e7261.png', 1, '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
    ('https://img.elo7.com.br/product/zoom/36549DC/arquivo-agenda-escolar-minnie-arquivo-agenda-minnie.jpg', 1, '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),

    -- Agenda Mickey
    ('https://dac.com.br/wp-content/uploads/2022/04/3655-Agenda-A5-2023-Mickey_frente.jpg', 2, 'abc123'),
    ('https://www.daclojaonline.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/g/agenda-a5-2025-mickey-4583_2.jpg', 2, 'abc123'),

    -- Caderno One Piece
    ('https://down-br.img.susercontent.com/file/br-11134201-23020-1w2je3ot22nv4d', 3, 'abc123'),
    ('https://d1zvfmhlebc91g.cloudfront.net/n49shopv2_papelecia/images/products/6568ed12b0a70/7891027340445_5_6541683aca0b0-6568ed12b0acb.jpg', 3, 'abc123'),

    -- Caneca Kratos
    ('https://cdn.dooca.store/105509/products/ysqioyql3kimiytgxitmfdecteve8oj3dxnm_640x640+fill_ffffff.jpg?v=1695482338&webp=0', 4, 'abc123'),

    -- Caneca Playstation
    ('https://images.tcdn.com.br/img/img_prod/460977/caneca_playstation_preta_cd_60599_1_70eec11fe05028e6e5b3fd3d4b31e71a.jpg', 5, 'abc123'),

    -- Caneca Gamer
    ('https://cdn.awsli.com.br/800x800/186/186813/produto/120124877/caneca-ps-gamer-efd03081.jpg', 6, 'abc123'),
    ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSxgkZLXfQ-6Q1tjHNJqFwgxwTh5qgeRL5kwbjFRVnyplY8LV85OHZeWLUVQeIJlne9e8&usqp=CAU', 6, 'abc123'),

    -- Caneca Java
    ('https://cdn.awsli.com.br/608/608801/produto/27768511/d3aea2373e.jpg', 7, 'abc123');

INSERT INTO personalizacao (nome_personalizacao, tipo_personalizacao, fk_produto)
VALUES ('Personalização de Capa', 'Texto', 1),
       ('Personalização de Datas', 'Texto', 2),
       ('Design Especial para Casamento', 'Design', 4),
       ('Mensagem Especial', 'Texto', 3),
       ('Mensagem para Faixa', 'Texto', 6);

INSERT INTO opcao_personalizacao (nome_opcao, descricao, acrescimo_opcao, url_img_opcao, fk_personalizacao,
                                  id_img_drive)
VALUES ('Texto em Ouro', 'Texto na capa em efeito dourado', 5.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1cCAa5y637fsJ4VZ96Lqz5Utz_ZcZKo49'),
       ('Texto em Prata', 'Texto na capa em efeito prateado', 4.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1OLxbmeet0tTxCn5BOIas0LmB7Y4G7pbf'),
       ('Data em Dourado', 'Datas em efeito dourado', 3.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 3, 'abc123'),
       ('Data em Prata', 'Datas em efeito prateado', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 3, 'abc123'),
       ('Design Floral', 'Design floral para casamentos', 8.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 4, 'abc123'),
       ('Design Minimalista', 'Design minimalista para casamentos', 6.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 4,
        'abc123'),
       ('Mensagem em Dourado', 'Mensagem em efeito dourado', 4.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Prata', 'Mensagem em efeito prateado', 3.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Vermelho', 'Mensagem em efeito vermelho', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Azul', 'Mensagem em efeito azul', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Verde', 'Mensagem em efeito verde', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Amarelo', 'Mensagem em efeito amarelo', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Rosa', 'Mensagem em efeito rosa', 2.00, 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123');

INSERT INTO material_produto (fk_material, fk_produto, qtd_material_necessario)
VALUES (1, 1, 2),
       (2, 1, 1),
       (1, 2, 3),
       (2, 2, 1),
       (1, 3, 1),
       (2, 3, 1),
       (3, 4, 1),
       (4, 5, 5),
       (1, 6, 2);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Cláudio Araújo', 'claudio@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
        '(11) 98765-4321', 'HABILITADO',
        NOW(), NOW(), '123.456.789-09', 1, 'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2005-01-07', NULL),

        ('Kauã Nunes', 'kaua@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER',
                '(11) 96280-7541', 'HABILITADO',
                NOW(), NOW(), '123.456.789-10', 1, 'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2003-05-22', NULL),

        ('Guilherme Santiago', 'guilherme@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
                        '(11) 91547-7541', 'HABILITADO',
                        NOW(), NOW(), '321.456.789-10', 1, 'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2005-03-12', NULL),

        ('Matheus Munari', 'mateus@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
                       '(11) 98304-7541', 'HABILITADO',
                       NOW(), NOW(), '123.654.789-10', 1, 'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2000-04-09', NULL);

INSERT INTO AVALIACAO (id_avaliacao, titulo, nota_avaliacao, avaliacao_aprovada, descricao, produto_id, usuario_id) VALUES
    (1, 'Melhor Agenda', 4, true, 'Produto Bom! A agenda é muito útil.', 1, 1),
    (2, 'Agenda Perfeita', 5, true, 'Produto excelente! A agenda é linda e muito útil.', 1, 2),
    (3, 'Boa Agenda', 3, true, 'Produto Ok, A agenda é útil.', 1, 3),
    (4, 'Não curti', 2, true, 'Produto mais ou menos! A agenda é de bom material.', 1, 4),
    (9, 'Muito Bom', 4, true, 'Produto Bom! A agenda é muito útil.', 1, 1),
    (10, 'Agenda Muito boa', 5, true, 'Produto excelente! A agenda é linda e muito útil.', 1, 2),
    (11, 'Boa Agenda', 4, true, 'Produto Ok, A agenda é útil.', 1, 3),
    (12, 'Curti demais', 5, true, 'Produto muito bom! A agenda é de bom material.', 1, 4);

INSERT INTO AVALIACAO (id_avaliacao, titulo, nota_avaliacao, avaliacao_aprovada, descricao, produto_id, usuario_id) VALUES
    (5, 'Curti muito', 4, true, 'Produto muito Bom! A agenda é muito legal.', 2, 1),
    (6, 'Agenda Perfeita', 5, true, 'Produto excelente! A agenda é perfeita.', 2, 2),
    (7, 'Ok', 3, true, 'Produto Ok, A agenda é de boa qualidade.', 2, 3),
    (8, 'Demorou muito', 2, true, 'O produto é bom, mas demorou muito para chegar', 2, 4);