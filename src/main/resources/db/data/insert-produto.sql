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
