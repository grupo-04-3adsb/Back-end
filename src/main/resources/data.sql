-- RUNSCRIPT FROM 'classpath:db/data/insert-categoria.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-subcategoria.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-material.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-produto.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-imagens-produto.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-personalizacoes.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-opcao-personalizacao.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-material-produto.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-usuarios.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-avaliacao.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-endereco.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-pedido.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-item-pedido.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-personalizacao-item-pedido.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-responsavel-pedido.sql';
-- RUNSCRIPT FROM 'classpath:db/data/insert-custos-outros.sql';

INSERT INTO categoria (nome_categoria, categoria_ativa)
VALUES ('Papelaria e Escritório', true),
       ('Festas e Eventos', true),
       ('Adesivos', true),
       ('Personalização e Brindes', true),
       ('Artesanato e Materiais Artísticos', true),
       ('Artigos para Datas Comemorativas', true);

INSERT INTO subcategoria (nome_subcategoria, descricao_subcategoria, fk_categoria, subcategoria_ativa)
VALUES ('Agendas e Cadernos', 'Cadernos e agendas personalizadas e decoradas', 1, true),
       ('Canecas', 'Canecas decorativas e personalizadas', 3, true),
       ('Decoração para Festas', 'Decoração', 2, true),
       ('Camisetas Temáticas', 'Camisetas com estampas personalizadas e de personagens', 3, true),
       ('Decoração de Mesa para Festas', 'Itens decorativos para mesas de festas', 2, true),
       ('Brindes Corporativos', 'Itens promocionais personalizados para empresas', 3, true),
       ('Material para Scrapbook e Pintura', 'Material para criação de scrapbook e pintura', 4, true),
       ('Decoração Natalina', 'Itens de decoração para o Natal', 5, true),
       ('Brinquedos Educativos', 'Brinquedos que estimulam o aprendizado', 6, true);

INSERT INTO material (nome_material, preco_unitario, preco_pacote, unidades_por_pacote, estoque, data_hora_cadastro,
                      data_hora_atualizacao)
VALUES ('Papel', 0.20, 1.00, 500, 5, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Cartolina', 0.50, 2.00, 200, 10, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Tecido', 1.00, 5.00, 100, 3, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Fita', 0.75, 3.00, 150, 4, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Plástico', 0.30, 0.75, 300, 2, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caneta', 1.50, 1.50, 100, 20, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Lápis', 0.80, 0.80, 100, 15, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caderno', 2.50, 5.00, 50, 8, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Giz de cera', 0.90, 1.00, 50, 12, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Borrachas', 0.60, 0.50, 100, 25, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Marcador permanente', 1.20, 1.50, 80, 18, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Lápis de cor', 1.10, 1.20, 70, 22, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Tesoura', 3.00, 6.00, 20, 7, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Cola', 1.40, 2.00, 30, 15, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Fita adesiva', 0.90, 1.50, 25, 11, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Clip', 0.05, 0.10, 500, 100, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Grampeador', 4.00, 8.00, 10, 5, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Pasta arquivo', 1.80, 3.00, 20, 9, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Régua', 0.60, 1.00, 50, 20, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caderno brochura', 2.00, 4.00, 30, 14, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caderno espiral', 3.50, 5.00, 20, 10, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caneta marca-texto', 1.75, 2.50, 25, 16, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Pincel', 1.00, 2.00, 10, 8, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Tinta guache', 2.00, 5.00, 12, 6, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Papel cartão', 0.40, 1.00, 100, 12, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Corda', 0.25, 0.50, 150, 30, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Caixa de som', 15.00, 100.00, 5, 2, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Baterias', 2.50, 10.00, 20, 20, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Rolo de fita crepe', 1.00, 2.50, 15, 15, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Capa de chuva', 5.00, 10.00, 10, 10, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Protetor auricular', 2.00, 5.00, 20, 10, DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Papel Fotográfico  1/2 A4 - 230g', 0.17, null, null, null,
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()), -- id: 32
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Miolo offset A6 - 90g', 0.03, null, null, null, DATEADD('DAY', FLOOR(RAND() * 365), NOW()), -- id: 33
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Impressão 1/2 colorida', 0.25, null, null, null, DATEADD('DAY', FLOOR(RAND() * 365), NOW()), -- id: 34
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Laminção 1/2 A4', 0.60, null, null, null, DATEADD('DAY', FLOOR(RAND() * 365), NOW()), -- id: 35
        DATEADD('DAY', FLOOR(RAND() * 365), NOW())),
       ('Espiral transparente 9mm 1/2', 0.15, null, null, null, DATEADD('DAY', FLOOR(RAND() * 365), NOW()), -- id: 36
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()));

INSERT INTO produto (nome, preco_venda, descricao, dimensao, desconto, margem_lucro, sku, url_imagem_principal,
                     personalizavel, personalizacao_obrigatoria, fk_categoria, fk_subcategoria, id_img_drive,
                     produto_Ativo, data_hora_cadastro, data_hora_atualizacao, peso)
VALUES
    -- Produtos de Papelaria
    ('Agenda Minnie', 34.90, 'Agenda decorada com a personagem Minnie.', '21x14x0', 0, 0.30, 'AGENDA-MINNIE',
     'https://ascriativasloja.com.br/wp-content/uploads/2023/01/1-12.jpg', true, true, 1, 1,
     '1OosCxJEq0zdIUuuzpNhzRafG3N6eEyG5', true, NOW(), NOW(), 0.50),
    ('Agenda Mickey', 34.90, 'Agenda decorada com a personagem Mickey.', '21x14x0', 10, 0.30, 'AGENDA-MICKEY',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTh9GrMNq4LrRs6X1Vi4vko2drhPqjBDDwfGVaAaIHiTS2_Dq6TLN96sDZLr4MmBXGYGYg&usqp=CAU',
     true, true, 1, 1, '1bXl8JQW_FQ8i9-OeMcC_2KYss5_3TeWB', true, NOW(), NOW(), 0.50),
    ('Caderno One Piece', 29.90, 'Caderno com capa de One Piece.', '21x14x0', 0, 0.30, 'CADERNO-ONEPIECE',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiV1TKjZCc9HeLbdhECyNToe6Mk_JntIj9WQ&s', true, true, 1,
     1, '1yI3d4MMQfRuXY85iw2ZKXxTC2aqh-70', true, NOW(), NOW(), 0.50),

    -- Canecas
    ('Caneca Kratos', 29.90, 'Caneca com o tema Kratos.', '8x8x10', 0, 0.30, 'CANECA-KRATOS',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB3AXRDyuNngaF58r6ZdNGE4b3D8Br2XVGEQ&s', false, false, 2,
     2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Caneca Playstation', 29.90, 'Caneca com o tema Playstation.', '8x8x10', 10, 0.30, 'CANECA-PLAYSTATION',
     'https://cdn.awsli.com.br/600x450/1225/1225697/produto/130152295/9ee6196526.jpg', false, false, 2, 2,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Caneca Gamer', 29.90, 'Caneca com tema gamer.', '8x8x10', 10, 0.30, 'CANECA-GAMER',
     'https://images.tcdn.com.br/img/img_prod/723087/caneca_gamer_i_love_you_3653_1_20201214005540.jpg', false,
     false, 2, 2, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Caneca Java', 29.90, 'Caneca com tema Java.', '8x8x10', 10, 0.30, 'CANECA-JAVA',
     'https://cdn.awsli.com.br/608/608801/produto/25906868/caneca-coffee-java-ee4fc9fb.jpg', false, false, 2, 2,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),

    -- Camisetas
    ('Camisa Angry Birds', 49.90, 'Camisa personalizada com Angry Birds.', '70x50x0', 10, 0.30, 'CAMISA-ANGRYBIRDS',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDzqMgb38UYNyTDTsD_E-Mc6L1Gc5PVvDESw&s', true, true, 2,
     3, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Camisa Pokemon', 49.90, 'Camisa personalizada com Pikachu.', '70x50x0', 10, 0.30, 'CAMISA-POKEMON',
     'https://cdn.dooca.store/292/products/camiseta-pokemon-pikachu-choque-do-trovao-aberta.jpg?v=1585147881', true,
     true, 2, 3, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),

    -- Decoração
    ('Topo de Bolo Vingadores', 49.90, 'Topo de bolo com tema Vingadores.', '30x20x0', 0.15, 0.25,
     'TOPO-BOLO-VINGADORES', 'https://cdn.awsli.com.br/600x1000/761/761999/produto/163707609ce03d02cb1.jpg', false,
     false, 2, 4, '1bJ4H3RLbY8w2I8Gth7_yTVPrbNYgDnv', true, NOW(), NOW(), 0.50),
    ('Topo de Bolo Barbie', 49.90, 'Topo de bolo com tema Barbie.', '30x20x0', 0.15, 0.25, 'TOPO-BOLO-BARBIE',
     'https://img.elo7.com.br/product/685x685/44E707C/topo-de-bolo-barbie-decoracao-barbie.jpg', false, false, 2, 4,
     '1bJ4H3RLbY8w2I8Gth7_yTVPrbNYgDnv', true, NOW(), NOW(), 0.50),

    -- Adesivos
    ('Adesivo Naruto', 9.90, 'Adesivo com tema Naruto.', '10x10x0', 5, 0.20, 'ADESIVO-NARUTO',
     'https://http2.mlstatic.com/D_NQ_NP_958149-MLB54876142860_042023-O.webp', false, false, 3, 8,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Adesivo Dev', 9.90, 'Adesivo com tema Dev.', '10x10x0', 5, 0.20, 'ADESIVO-DEV',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkk2ZgCo8-uC1N7nayU5CSl1V4EDI9UuJByrR7mJZUqncpTtKtA5s6htzV-wXcOFagMdU&usqp=CAU',
     false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Adesivo Star Wars', 9.90, 'Adesivo com tema Star Wars.', '10x10x0', 5, 0.20, 'ADESIVO-STARWARS',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7k3xPJfKnfXu-LUqTbPPTJ6Qs1f2hbnGvaw&s', false, false, 3,
     8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Adesivo Harry Potter', 9.90, 'Adesivo com tema Harry Potter.', '10x10x10', 5, 0.20, 'ADESIVO-HARRYPOTTER',
     'https://images.tcdn.com.br/img/img_prod/1083971/adesivos_harry_potter_kit_com_48un_6cm_brindes_51_1_901e122a6c63cff652d4b020c3fa4715.jpg',
     false, false, 3, 8, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50)
        ,
    ('Adesivo de Música', 9.90, 'Adesivo com tema de música.', '10x10x10', 5, 0.20, 'ADESIVO-MUSICA',
     'https://http2.mlstatic.com/D_NQ_NP_828482-MLB72737668006_112023-O.webp', false, false, 3, 8,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Adesivo Retro', 9.90, 'Adesivo com tema retro de jogos.', '10x10x10', 5, 0.20, 'ADESIVO-RETRO',
     'https://http2.mlstatic.com/D_NQ_NP_702242-MLB77858802843_072024-O.webp', false, false, 3, 8,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Adesivo de Cidades', 9.90, 'Adesivo com silhuetas de cidades famosas.', '10x10x10', 5, 0.20,
     'ADESIVO-CIDADES', 'https://http2.mlstatic.com/D_NQ_NP_948189-MLB70119768954_062023-O.webp', false, false, 3, 8,
     '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),

    -- Brindes
    ('Brinde Corporativo', 12.90, 'Brinde personalizado para eventos corporativos.', '10x10x10', 10, 0.25,
     'BRINDE-001', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSy9wYDYpAdtUKZwRv1m3kMZE0Wszg6XEI2DQ&s',
     true, true, 1, 5, '1jW2nqf9hZUyJ3p7Qah4D2azP-HM7sG5', true, NOW(), NOW(), 0.50),

    -- Materiais de Escritório
    ('Calendário Empresarial', 19.90, 'Calendário para empresas.', '10x10x10', 5, 0.20, 'CALENDARIO-EMPRESARIAL',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQF8eyt-9h1u1qiLnDr6VvENbOQWxWw0vF6g&s', false, false, 1,
     5, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50),
    ('Caneta Empresarial', 12.90, 'Caneta personalizada para empresas.', '10x10x10', 5, 0.20, 'CANETA-EMPRESARIAL',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgorUHkv--OP-M2dQx5Mqgv2xkpd8yftQHMA&s', false, false, 1,
     5, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true, NOW(), NOW(), 0.50);

INSERT INTO imagens_produto (url_img_adicional, fk_produto, id_img_drive)
VALUES
    -- Agenda Minnie
    ('https://down-br.img.susercontent.com/file/br-11134207-7r98o-ll12v9cj8igic1', 1,
     '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
    ('https://images.tcdn.com.br/img/img_prod/1090591/agenda_datada_2022_minnie_vermelha_1_807_1_9fbda09577108ffeeeefbbadcb2e7261.png',
     1, '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
    ('https://img.elo7.com.br/product/zoom/36549DC/arquivo-agenda-escolar-minnie-arquivo-agenda-minnie.jpg', 1,
     '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),

    -- Agenda Mickey
    ('https://dac.com.br/wp-content/uploads/2022/04/3655-Agenda-A5-2023-Mickey_frente.jpg', 2, 'abc123'),
    ('https://www.daclojaonline.com.br/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/g/agenda-a5-2025-mickey-4583_2.jpg',
     2, 'abc123'),

    -- Caderno One Piece
    ('https://down-br.img.susercontent.com/file/br-11134201-23020-1w2je3ot22nv4d', 3, 'abc123'),
    ('https://d1zvfmhlebc91g.cloudfront.net/n49shopv2_papelecia/images/products/6568ed12b0a70/7891027340445_5_6541683aca0b0-6568ed12b0acb.jpg',
     3, 'abc123'),

    -- Caneca Kratos
    ('https://cdn.dooca.store/105509/products/ysqioyql3kimiytgxitmfdecteve8oj3dxnm_640x640+fill_ffffff.jpg?v=1695482338&webp=0',
     4, 'abc123'),

    -- Caneca Playstation
    ('https://images.tcdn.com.br/img/img_prod/460977/caneca_playstation_preta_cd_60599_1_70eec11fe05028e6e5b3fd3d4b31e71a.jpg',
     5, 'abc123'),

    -- Caneca Gamer
    ('https://cdn.awsli.com.br/800x800/186/186813/produto/120124877/caneca-ps-gamer-efd03081.jpg', 6, 'abc123'),
    ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSxgkZLXfQ-6Q1tjHNJqFwgxwTh5qgeRL5kwbjFRVnyplY8LV85OHZeWLUVQeIJlne9e8&usqp=CAU',
     6, 'abc123'),

    -- Caneca Java
    ('https://cdn.awsli.com.br/608/608801/produto/27768511/d3aea2373e.jpg', 7, 'abc123');

INSERT INTO personalizacao (nome_personalizacao, tipo_personalizacao, fk_produto, personalizacao_ativa)
VALUES ('Personalização de Capa', 'Texto', 1, false),
       ('Personalização de Datas', 'Texto', 2, true),
       ('Design Especial para Casamento', 'Imagem', 4, true),
       ('Mensagem Especial', 'Seleção', 3, true),
       ('Mensagem para Faixa', 'Texto', 6, true),
       ('Cor do texto', 'Seleção', 1, true),
       ('Imagem do caderno', 'Imagem', 1, true);

INSERT INTO opcao_personalizacao (nome_opcao, descricao, acrescimo_opcao, url_img_opcao, fk_personalizacao,
                                  id_img_drive)
VALUES ('Título', 'Texto na capa em efeito dourado', 5.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1cCAa5y637fsJ4VZ96Lqz5Utz_ZcZKo49'),
       ('Subtítulo', 'Texto na capa em efeito prateado', 4.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1OLxbmeet0tTxCn5BOIas0LmB7Y4G7pbf'),
       ('Data em Dourado', 'Datas em efeito dourado', 3.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 3, 'abc123'),
       ('Data em Prata', 'Datas em efeito prateado', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 3, 'abc123'),
       ('Design Floral', 'Design floral para casamentos', 8.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 4, 'abc123'),
       ('Design Minimalista', 'Design minimalista para casamentos', 6.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 4,
        'abc123'),
       ('Mensagem em Dourado', 'Mensagem em efeito dourado', 4.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Prata', 'Mensagem em efeito prateado', 3.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Vermelho', 'Mensagem em efeito vermelho', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Azul', 'Mensagem em efeito azul', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Verde', 'Mensagem em efeito verde', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Amarelo', 'Mensagem em efeito amarelo', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Mensagem em Rosa', 'Mensagem em efeito rosa', 2.00,
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('Vermelho', 'Texto em vermelho', 1.00, 'https://placehold.co/100', 6, 'abc123'),
       ('Azul', 'Texto em azul', 1.00, 'https://placehold.co/100', 6, 'abc123'),
       ('Verde', 'Texto em verde', 1.00, 'https://placehold.co/100', 6, 'abc123'),
       ('Amarelo', 'Texto em amarelo', 1.00, 'https://placehold.co/100', 6, 'abc123'),
       ('Rosa', 'Texto em rosa', 1.00, 'https://placehold.co/100', 6, 'abc123'),
       ('Imagem de capa', 'Imagem de coração', 3.00, 'https://placehold.co/100', 7, 'abc123');

INSERT INTO material_produto (fk_material, fk_produto, qtd_material_necessario)
VALUES (1, 1, 2),
       (2, 1, 1),
       (1, 2, 3),
       (2, 2, 1),
       (1, 3, 1),
       (2, 3, 1),
       (3, 4, 1),
       (4, 5, 5),
       (1, 6, 2),
       (32, 11, 1),
       (33, 11, 30),
       (34, 11, 1),
       (35, 11, 1),
       (36, 11, 1);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Cláudio Araújo', 'claudio@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
        '(11) 98765-4321', 'HABILITADO', DATEADD('DAY', FLOOR(RAND() * 365), NOW()),
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()), '123.456.789-09', 1,
        'https://drive.google.com/thumbnail?id=1XIwVniVviUmZfMycRm7qj14IXefBkk1l&sz=w1000', '2005-01-07', NULL),
       ('Cláudio Araújo', 'claudiouser@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC',
        'USER',
        '(11) 98765-4321', 'HABILITADO',
        DATEADD('DAY', FLOOR(RAND() * 365), NOW()), DATEADD('DAY', FLOOR(RAND() * 365), NOW()), '123.456.789-08', 1,
        'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2005-01-07', NULL),
       ('Matheus Munari', 'matheus_munari_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4323',
        'HABILITADO',
        NOW(), NOW(), '321.654.987-01', 1,
        'https://drive.google.com/thumbnail?id=1mbQFeK1UuV0RqLTdoPHzaYR3u-xHQuRW&sz=w1000', '1990-05-10', NULL),
       ('Matheus Munari', 'matheus_munari_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4324', 'HABILITADO',
        NOW(), NOW(), '321.654.987-02', 1, 'https://drive.google.com/thumbnail?id=img2', '1990-05-10', NULL),
       ('Matheus Kikuti', 'matheus_kikuti_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4325',
        'HABILITADO',
        NOW(), NOW(), '456.789.123-03', 1,
        'https://drive.google.com/thumbnail?id=1951U5DoFHk200f75sOBRoVc09x8G9f0j&sz=w1000', '1992-08-15', NULL),
       ('Matheus Kikuti', 'matheus_kikuti_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4326', 'HABILITADO',
        NOW(), NOW(), '456.789.123-04', 1, 'https://drive.google.com/thumbnail?id=img3', '1992-08-15', NULL),
       ('Guilherme Santiago', 'guilherme_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4327', 'HABILITADO',
        NOW(), NOW(), '654.321.987-05', 1,
        'https://drive.google.com/thumbnail?id=1qYns6JdeRRiz0iZqEc77u52Cw4CFgQUb&sz=w1000', '1995-12-20', NULL),
       ('Guilherme Santiago', 'guilherme_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4328', 'HABILITADO',
        NOW(), NOW(), '654.321.987-06', 1, 'https://drive.google.com/thumbnail?id=img4', '1995-12-20', NULL),
       ('Kauã Nunes', 'kaua_admin@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
        '(11) 98765-4329', 'HABILITADO',
        NOW(), NOW(), '789.654.321-07', 1,
        'https://drive.google.com/thumbnail?id=11Uwm72ZzSqqGfF_FX6kDixuTHA0rSKFW&sz=w1000', '2000-02-05', NULL),
       ('Kauã Nunes', 'kaua_user@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER',
        '(11) 98765-4330', 'HABILITADO',
        NOW(), NOW(), '789.654.321-08', 1, 'https://drive.google.com/thumbnail?id=img5', '2000-02-05', NULL);

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

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua Londres', '68', 'Casa', 'Bairro Jardim das Nações', 'Diadema', 'SP', '09930-220', 'Brasil',
        'Aperte a campainha',
        TRUE, 'RUA', 1),
       ('Rua Londres', '68', 'Casa', 'Bairro Jardim das Nações', 'Diadema', 'SP', '09930-220', 'Brasil',
        'Aperte a campainha',
        TRUE, 'RUA', 2),
       ('Rua B', '456', 'Casa', 'Bairro Norte', 'São Paulo', 'SP', '02002-000', 'Brasil', 'Tocar a campainha', TRUE,
        'RUA', 3),
       ('Rua B', '789', 'Apto 202', 'Bairro Norte', 'São Paulo', 'SP', '02003-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 4),
       ('Rua C', '101', 'Casa 3', 'Bairro Leste', 'São Paulo', 'SP', '03004-000', 'Brasil', 'Tocar duas vezes', TRUE,
        'RUA', 5),
       ('Rua C', '102', 'Apto 303', 'Bairro Leste', 'São Paulo', 'SP', '03005-000', 'Brasil', 'Deixar com o porteiro',
        TRUE, 'RUA', 6),
       ('Rua D', '123', 'Casa 1', 'Bairro Sul', 'São Paulo', 'SP', '04006-000', 'Brasil', 'Deixar na garagem', TRUE,
        'RUA', 7),
       ('Rua D', '124', 'Apto 404', 'Bairro Sul', 'São Paulo', 'SP', '04007-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 8),
       ('Rua E', '125', 'Casa 2', 'Bairro Oeste', 'São Paulo', 'SP', '05008-000', 'Brasil', 'Deixar na garagem', TRUE,
        'RUA', 9),
       ('Rua E', '126', 'Apto 505', 'Bairro Oeste', 'São Paulo', 'SP', '05009-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 1),
       ('Rua F', '127', 'Casa 3', 'Bairro Centro', 'São Paulo', 'SP', '06010-000', 'Brasil', 'Deixar na garagem', FALSE,
        'RUA', 2);

INSERT INTO PEDIDO (NOME_USUARIO, TOTAL, VALOR_DESCONTO, VALOR_FRETE, NUM_PARCELA, VALOR_PARCELA, FORMA_PAGAMENTO,
                    STATUS, OBSERVACAO, DATA_PEDIDO, DATA_ENTREGA, DATA_PAGAMENTO, DATA_CANCELAMENTO, DATA_ATUALIZACAO,
                    FK_ENDERECO_ENTREGA, FK_USUARIO, DATA_CONCLUSAO)
VALUES ('Matheus Munari', 0.01, 10.00, 20.00, 3, 83.33, '', 'CARRINHO', 'Entrega rápida', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NULL, NULL, NOW(), 3, 2, NULL),

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
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 9, 6, DATEADD(DAY, FLOOR(RAND() * 365), NOW())),

       ('Matheus Kikuti', 250.00, 25.00, 12.00, 3, 83.33, 'Cartão de Débito', 'CARRINHO', 'Entrega normal', NOW(),
        DATEADD(DAY, FLOOR(RAND() * 365), NOW()), NOW(), NULL, NOW(), 9, 6, DATEADD(DAY, FLOOR(RAND() * 365), NOW()));

INSERT INTO ITEM_PEDIDO (QUANTIDADE, PRECO_UNITARIO, VALOR_TOTAL, DESCONTO, VALOR_DESCONTO, VALOR_FRETE, FK_PRODUTO,
                         FK_PEDIDO)
VALUES (1, 50.00, 50.00, 5.0, 2.50, 5.00, 1, 2),
       (2, 75.00, 150.00, 10.0, 15.00, 10.00, 4, 3),
       (3, 100.00, 300.00, 15.0, 45.00, 15.00, 5, 4),
       (1, 50.00, 50.00, 5.0, 2.50, 5.00, 6, 5),
       (2, 75.00, 150.00, 10.0, 15.00, 10.00, 2, 2),
       (3, 100.00, 300.00, 15.0, 45.00, 15.00, 8, 2),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 10),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 1),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 8),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 7),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 8),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 1),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 9),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 10),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 1),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 1),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 7),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 5, 6),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 7),
       (2, 100.00, 200.00, 5.0, 10.00, 10.00, 8, 6),
       (1, 60.00, 60.00, 0.0, 0.00, 10.00, 6, 5);

INSERT INTO PERSONALIZACAO_ITEM_PEDIDO(DESCRICAO_PERSONALIZACAO, VALOR_PERSONALIZACAO, FK_ITEM_PEDIDO,
                                       FK_PERSONALIZACAO, FK_OPCAO_PERSONALIZACAO)
VALUES ('Cláudio', 4.0, 1, 1, 1);

INSERT INTO RESPONSAVEL_PEDIDO (FK_PEDIDO, FK_RESPONSAVEL, DATA_HORA_CADASTRO, DATA_HORA_ATUALIZACAO)
VALUES (2, 1, NOW(), NOW()),
       (3, 3, NOW(), NOW()),
       (4, 5, NOW(), NOW()),
       (2, 5, NOW(), NOW()),
       (1, 3, NOW(), NOW()),
       (5, 7, NOW(), NOW()),
       (1, 1, NOW(), NOW()),
       (2, 3, NOW(), NOW()),
       (3, 5, NOW(), NOW()),
       (4, 7, NOW(), NOW()),
       (5, 1, NOW(), NOW()),
       (10, 1, NOW(), NOW()),
       (9, 3, NOW(), NOW()),
       (8, 5, NOW(), NOW()),
       (7, 7, NOW(), NOW()),
       (6, 9, NOW(), NOW()),
       (5, 3, NOW(), NOW()),
       (4, 1, NOW(), NOW()),
       (3, 1, NOW(), NOW()),
       (2, 9, NOW(), NOW()),
       (1, 7, NOW(), NOW());

INSERT INTO CUSTO_OUTROS(descricao, valor)
VALUES ('Custos Outros - Luz, Net - R$ 240', 0.38),
       ('Impressora, R$ 1.200', 0.2),
       ('Tinta R$ 300 a cada 2 meses R$ 1800', 0.30),
       ('Perda de Materiais 5%', 0.09);

INSERT INTO parametro_geral(parametro_geral_id, parametro_geral_valor, parametro_geral_descricao, parametro_geral_tipo, usuario_criacao)
VALUES ('PROJECAO_VENDAS', '100','Valor utilizado para diluir os custos outros no preço dos produtos.', 'NEGOCIO', 'Sistema');

INSERT INTO faq (titulo, resposta) VALUES
('Como faço para trocar a senha?', 'Para trocar a senha, acesse o menu de configurações e clique em "Alterar senha".'),
('Quais formas de pagamento são aceitas?', 'Aceitamos cartões de crédito, débito, boleto bancário e Pix.'),
('Qual o prazo de entrega dos produtos?', 'O prazo de entrega varia conforme a localização e o método de envio escolhido. Consulte o prazo estimado no momento da compra.'),
('Como posso rastrear meu pedido?', 'Após a confirmação do envio, você receberá um código de rastreamento por e-mail para acompanhar seu pedido no site da transportadora.'),
('Posso devolver um produto?', 'Sim, aceitamos devoluções dentro do prazo de 7 dias corridos após o recebimento. O produto deve estar em perfeitas condições e com a embalagem original.'),
('O site é seguro para compras?', 'Sim, utilizamos criptografia SSL para garantir a segurança de suas informações durante a compra.');

INSERT INTO pagina_info(pagina_info_id, pagina_info_titulo, pagina_info_descricao, pagina_info_destino) VALUES
(1, 'Sobre a TCAteliê', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt ultricies. Nullam nec purus nec nunc tincidunt ultricies. Nullam nec purus nec nunc tincidunt ultricies. Nullam nec purus nec nunc tincidunt ultricies.', '/sobre');

INSERT INTO banner(banner_id, banner_titulo, banner_descricao, banner_button_text, banner_button_link, banner_imagem, banner_ativo, pagina_info_id) VALUES
(1, 'Conheça nossa história', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt ultricies.', 'Saiba mais', '/sobre', 'https://img.elo7.com.br/product/zoom/FCE084/quadro-paisagem-quadro-paisagem.jpg', true, 1);

INSERT INTO conteudo_dinamico(conteudo_dinamico_id, conteudo_dinamico_titulo, conteudo_dinamico_descricao, conteudo_dinamico_button_text, conteudo_dinamico_button_link, conteudo_dinamico_ativo, conteudo_dinamico_html, pagina_info_id) VALUES
(1, "XPTO1", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially", null, null, true, null, 1),
(2, "XPTO2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially", null, null, true, null, 1),
(3, "NOSSO ESPAÇO CRIATIVO", "Conheça o ambiente onde ideias ganham vida e cada detalhe é cuidadosamente trabalhado.", null, null, true, null, 1);

INSERT INTO conteudo_dinamico_imagens (conteudo_dinamico_id, imagem) VALUES
(1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVcPmdJqd5n62CDtn2csHXdmz8Bf4k7eKVBQ&s'),
(2, 'https://cdn.vnda.com.br/tucumbrasil/2024/12/10/12_25_36_176_12_12_8_854_roupas_indc3adgenas_019.jpg?v=1733847128'),
(3, 'https://www.escoladefeltro.com.br/wp-content/uploads/2021/08/atelie-de-artesanato-capa.jpg');

INSERT INTO valores(valores_id, valores_titulo, valores_descricao, valores_ativo) VALUES
(1, 'Qualidade', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt ultricies.', true),
(2, 'Comprometimento', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt ultricies.', true),
(3, 'Inovação', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt ultricies.', true);

INSERT INTO depoimento (depoimento_nome, depoimento_descricao, depoimento_rede_social, depoimento_ativo, depoimento_data_usuario, depoimento_imagem)
VALUES
('Ana Souza', 'Adorei o serviço! Super recomendo a todos. A experiência foi excelente e o atendimento muito atencioso.', 'FACEBOOK', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Carlos Silva', 'Estou muito satisfeito com o produto. Ele superou minhas expectativas e chegou no prazo.', 'INSTAGRAM', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Fernanda Oliveira', 'A qualidade do produto é incrível. Foi exatamente o que eu esperava. Comprarei novamente.', 'TWITTER', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Juliana Costa', 'Fiquei muito feliz com o resultado. A entrega foi rápida e o produto chegou impecável.', 'LINKEDIN', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Rodrigo Pereira', 'Comprei várias vezes e sempre fico muito satisfeito. Recomendo muito a loja.', 'FACEBOOK', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Maria Lima', 'Excelente atendimento. A equipe é muito atenciosa e o produto é de alta qualidade.', 'INSTAGRAM', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Lucas Almeida', 'O serviço foi além das minhas expectativas. Entrega rápida e suporte excelente.', 'TWITTER', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Patricia Mendes', 'Muito bom. Super recomendo para quem busca qualidade e praticidade. Muito feliz com a compra.', 'LINKEDIN', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Paulo Martins', 'Amei o produto! A compra foi simples e o atendimento online foi muito eficiente.', 'FACEBOOK', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Isabela Rodrigues', 'Comprei de presente para minha amiga e ela adorou. Muito bom, com certeza irei comprar mais.', 'INSTAGRAM', TRUE, NOW(), 'https://plus.unsplash.com/premium_photo-1741194732682-21f3046cf1a6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');