INSERT INTO categoria (nome_categoria)
VALUES ('Materiais de Escritório'),
       ('Festas e Eventos'),
       ('Artesanato'),
       ('Produtos Personalizados'),
       ('Brindes Corporativos'),
       ('Acessórios para Festas'),
       ('Festas Temáticas'),
       ('Decoração de Interiores'),
       ('Decoração de Natal'),
       ('Bolsas e Acessórios'),
       ('Jogos e Brinquedos'),
       ('Artigos para Casamento'),
       ('Flores e Arranjos'),
       ('Produtos Ecológicos'),
       ('Moda e Vestuário'),
       ('Bijuterias e Acessórios'),
       ('Cosméticos e Beleza'),
       ('Equipamentos de Festa'),
       ('Alimentos e Bebidas'),
       ('Livros e Materiais Didáticos'),
       ('Tecnologia e Gadgets'),
       ('Esportes e Atividades ao Ar Livre'),
       ('Saúde e Bem-Estar'),
       ('Fotografia e Vídeo'),
       ('Gadgets de Cozinha'),
       ('Brinquedos Educativos'),
       ('Materiais para Scrapbook'),
       ('Arte e Pintura'),
       ('Produtos para Animais de Estimação'),
       ('Ferramentas e Materiais de Construção'),
       ('Moda Infantil'),
       ('Roupas de Bebê'),
       ('Artigos para o Dia das Mães'),
       ('Artigos para o Dia dos Pais'),
       ('Artigos para o Dia das Crianças'),
       ('Equipamentos Esportivos'),
       ('Roupas de Inverno'),
       ('Roupas de Verão'),
       ('Equipamentos de Camping'),
       ('Arte Digital'),
       ('Instrumentos Musicais'),
       ('Artigos para Academia'),
       ('Decoração para Festas de Aniversário'),
       ('Artigos para Festivais'),
       ('Presentes para Todas as Ocasiões'),
       ('Suprimentos para Escritório'),
       ('Artigos de Papelaria'),
       ('Equipamentos de Segurança');


INSERT INTO subcategoria (nome_subcategoria, descricao_subcategoria, fk_categoria)
VALUES ('Canetas e Lápis', 'Canetas e lápis de diversas cores e tipos', 1),
       ('Papelaria Personalizada', 'Papelaria personalizada para eventos', 1),
       ('Decoração para Festas', 'Itens decorativos para festas e eventos', 2),
       ('Festas Infantis', 'Decoração e artigos para festas infantis', 2),
       ('Artesanato em Papel', 'Materiais para fazer artesanato em papel', 3),
       ('Personalização de Brindes', 'Brindes personalizados para empresas', 4),
       ('Lembrancinhas', 'Lembrancinhas personalizadas para festas', 4),
       ('Camisetas Personalizadas', 'Camisetas com estampas personalizadas', 4),
       ('Brindes para Eventos Corporativos', 'Brindes para eventos e feiras', 5),
       ('Balões Personalizados', 'Balões com mensagens ou imagens', 6),
       ('Decoração de Mesa', 'Artigos para decoração de mesa de festas', 6),
       ('Bandeirinhas e Guirlandas', 'Bandeirinhas e guirlandas para festas', 6),
       ('Toalhas de Mesa', 'Toalhas de mesa personalizadas', 6),
       ('Decoração para Casamento', 'Artigos para decoração de casamentos', 11),
       ('Convites Personalizados', 'Convites feitos sob medida para eventos', 11),
       ('Flores Artificiais', 'Flores artificiais para decoração', 13),
       ('Arranjos Florais', 'Arranjos florais personalizados', 13),
       ('Produtos Sustentáveis', 'Artigos ecológicos e sustentáveis', 14),
       ('Bijuterias Artesanais', 'Bijuterias feitas à mão', 16),
       ('Cosméticos Naturais', 'Produtos de beleza feitos com ingredientes naturais', 17),
       ('Equipamentos de Som', 'Equipamentos para festas e eventos', 18),
       ('Gastronomia para Festas', 'Alimentos e bebidas para festas', 19),
       ('Brinquedos para Bebês', 'Brinquedos educativos e seguros para bebês', 25),
       ('Materiais para Pintura', 'Tintas e pincéis para pintura', 28),
       ('Produtos para Cães', 'Acessórios e brinquedos para cães', 29),
       ('Suprimentos para Jardinagem', 'Ferramentas e materiais para jardinagem', 29),
       ('Roupas para Crianças', 'Roupas e acessórios infantis', 30),
       ('Roupas de Festa', 'Roupas para ocasiões especiais', 30),
       ('Decoração de Natal', 'Itens para decoração natalina', 9),
       ('Artesanato com Madeira', 'Materiais e ferramentas para artesanato em madeira', 3),
       ('Equipamentos de Camping', 'Artigos e equipamentos para camping', 38),
       ('Instrumentos Musicais para Crianças', 'Instrumentos musicais educativos', 38),
       ('Equipamentos de Yoga', 'Acessórios e equipamentos para yoga', 37),
       ('Flores Naturais', 'Flores frescas para eventos', 13),
       ('Decoração para Festas de Aniversário', 'Itens para festas de aniversário', 40),
       ('Roupas de Verão', 'Roupas leves e confortáveis para o verão', 35),
       ('Artigos de Papelaria para Escritório', 'Suprimentos de papelaria para escritório', 1),
       ('Materiais para Scrapbook', 'Materiais para criação de scrapbook', 28),
       ('Brinquedos para Animais de Estimação', 'Brinquedos e acessórios para pets', 29),
       ('Suprimentos para Estudo', 'Materiais escolares e didáticos', 20),
       ('Decoração de Interiores', 'Artigos para decoração de ambientes', 8);

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

INSERT INTO produto (nome, preco_venda, descricao, dimensao, desconto, margem_lucro, sku, url_imagem_principal,
                     personalizavel, personalizacao_obrigatoria, fk_categoria, fk_subcategoria, id_img_drive,
                     produto_Ativo)
VALUES ('Caderno Personalizado', 29.90, 'Caderno com capa personalizada e folhas pautadas.', 'A5', 0.10, 0.30,
        'CADERNO-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true,
        1,
        1, '1OosCxJEq0zdIUuuzpNhzRafG3N6eEyG5', true),

       ('Canetas Coloridas', 15.90, 'Conjunto de 12 canetas coloridas para desenho.', '15cm', 0.05, 0.20,
        'CANETA-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', false, false,
        1,
        1, '1bXl8JQW_FQ8i9-OeMcC_2KYss5_3TeWB', true),

       ('Decoração de Mesa para Aniversário', 49.90, 'Conjunto de itens para decoração de mesa de aniversário.',
        '50x50cm', 0.15, 0.25,
        'DECORACAO-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', false,
        false,
        2,
        6, '1bJ4H3RLbY8w2I8Gth7_yTVPrbNYgDnv', false),

       ('Bolsa de Tecido', 79.90, 'Bolsa de tecido personalizada, ideal para eventos.', '40x30cm', 0.20, 0.35,
        'BOLSA-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true, 1,
        1, '1bmT3tK8ImTYHUEUB3J8kl5JHqfZ_4d8', true),

       ('Papel de Presente', 9.90, 'Papel de presente decorado, 5 metros.', '5m', 0.00, 0.15,
        'PAPEL-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', false, false,
        1,
        1, '1yLh7kLo0lFWxndjET_vZcOa9h3uTqPI', true),

       ('Brinde Corporativo', 12.90, 'Brinde personalizado para eventos corporativos.', '10x10cm', 0.10, 0.25,
        'BRINDE-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true, 5,
        9, '1jW2nqf9hZUyJ3p7Qah4D2azP-HM7sG5', true),

       ('Kit de Festa', 99.90, 'Kit completo para festa infantil com decoração e lembrancinhas.', '1,5m', 0.20, 0.30,
        'KITFESTA-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true,
        2,
        10, '1iXYHyacDGmqDa_Oz9n_NTAzI66AzXib', true),

       ('Convite de Casamento', 39.90, 'Convite personalizado para casamentos.', 'A5', 0.10, 0.20,
        'CONVITE-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true,
        11,
        13, '1_CIkSOZoyKyb8Yo_E1GUL4TjINu2i0g', true),

       ('Arranjo Floral', 59.90, 'Arranjo floral para decoração de eventos.', '30cm', 0.15, 0.25,
        'ARRANJO-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', false, false,
        13,
        14, '1zM10FnC2DNbZfSwqIpOCq-nFZlFk6ZY', true),

       ('Tinta Acrílica', 10.90, 'Tinta acrílica em diversas cores, 250ml.', '250ml', 0.05, 0.15,
        'TINTA-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', false, false,
        28,
        28, '1yI3d4MMQfRuXY85iw2ZKXxTC2aqh-70', true),

       ('Camiseta Personalizada', 29.90, 'Camiseta com estampa personalizada.', 'M', 0.10, 0.30,
        'CAMISETA-001', 'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', true, true,
        1,
        1, '1xq_dAKhsz4BxDB9d3gK7G8W0Wzk3MrP', true);

INSERT INTO imagens_produto (url_img_adicional, fk_produto, id_img_drive)
VALUES ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 1,
        '1kJUI8FDa3Z4qVDxRvS0wYzeZwGrt55r2'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 2, 'abc123'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 3, 'abc123'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 4, 'abc123'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123'),
       ('https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 6, 'abc123');

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
        'https://drive.google.com/thumbnail?id=1SStxF5xD5SM_HyDM_bk9rD0rY6y8jrOg&sz=w1000', 5, 'abc123');

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
        NOW(), NOW(), '123.456.789-08', 1,
        'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2005-01-07', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua Londres', '68', 'Casa', 'Bairro Jardim das Nações', 'Diadema', 'SP', '09930-220', 'Brasil',
        'Aperte a campainha',
        TRUE, 'RUA', 1);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Cláudio Araújo', 'claudio_user@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC',
        'USER', '(11) 98765-4322', 'HABILITADO',
        NOW(), NOW(), '123.456.789-09', 1,
        'https://drive.google.com/thumbnail?id=14bS2oXh2unEpaM2lgzwpqxPduDYvFsHB&sz=w1000', '2005-01-07', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua Londres', '68', 'Casa', 'Bairro Jardim das Nações', 'Diadema', 'SP', '09930-220', 'Brasil',
        'Aperte a campainha',
        TRUE, 'CASA', 2);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Matheus Munari', 'matheus_munari_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4323',
        'HABILITADO',
        NOW(), NOW(), '321.654.987-01', 1, 'https://drive.google.com/thumbnail?id=img2', '1990-05-10', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua B', '456', 'Casa', 'Bairro Norte', 'São Paulo', 'SP', '02002-000', 'Brasil', 'Tocar a campainha', TRUE,
        'RUA', 3);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Matheus Munari', 'matheus_munari_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4324', 'HABILITADO',
        NOW(), NOW(), '321.654.987-02', 1, 'https://drive.google.com/thumbnail?id=img2', '1990-05-10', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua B', '789', 'Apto 202', 'Bairro Norte', 'São Paulo', 'SP', '02003-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 4);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Matheus Kikuti', 'matheus_kikuti_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4325',
        'HABILITADO',
        NOW(), NOW(), '456.789.123-03', 1, 'https://drive.google.com/thumbnail?id=img3', '1992-08-15', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua C', '101', 'Casa 3', 'Bairro Leste', 'São Paulo', 'SP', '03004-000', 'Brasil', 'Tocar duas vezes', TRUE,
        'RUA', 5);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Matheus Kikuti', 'matheus_kikuti_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4326', 'HABILITADO',
        NOW(), NOW(), '456.789.123-04', 1, 'https://drive.google.com/thumbnail?id=img3', '1992-08-15', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua C', '102', 'Apto 303', 'Bairro Leste', 'São Paulo', 'SP', '03005-000', 'Brasil', 'Deixar com o porteiro',
        TRUE, 'RUA', 6);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Guilherme Santiago', 'guilherme_admin@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4327', 'HABILITADO',
        NOW(), NOW(), '654.321.987-05', 1, 'https://drive.google.com/thumbnail?id=img4', '1995-12-20', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua D', '123', 'Casa 1', 'Bairro Sul', 'São Paulo', 'SP', '04006-000', 'Brasil', 'Deixar na garagem', TRUE,
        'RUA', 7);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Guilherme Santiago', 'guilherme_user@gmail.com',
        '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER', '(11) 98765-4328', 'HABILITADO',
        NOW(), NOW(), '654.321.987-06', 1, 'https://drive.google.com/thumbnail?id=img4', '1995-12-20', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua D', '124', 'Apto 404', 'Bairro Sul', 'São Paulo', 'SP', '04007-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 8);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Kauã Nunes', 'kaua_admin@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN',
        '(11) 98765-4329', 'HABILITADO',
        NOW(), NOW(), '789.654.321-07', 1, 'https://drive.google.com/thumbnail?id=img5', '2000-02-05', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua E', '125', 'Casa 2', 'Bairro Oeste', 'São Paulo', 'SP', '05008-000', 'Brasil', 'Deixar na garagem', TRUE,
        'RUA', 9);

INSERT INTO USUARIO
(NOME_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, CARGO_USUARIO, TELEFONE_USUARIO, STATUS_USUARIO, DATA_CADASTRO_USUARIO,
 DATA_ATUALIZACAO_USUARIO, CPF_USUARIO, GENERO_USUARIO, URL_IMG_USUARIO, DATA_NASCIMENTO_USUARIO, ID_GOOGLE)
VALUES ('Kauã Nunes', 'kaua_user@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'USER',
        '(11) 98765-4330', 'HABILITADO',
        NOW(), NOW(), '789.654.321-08', 1, 'https://drive.google.com/thumbnail?id=img5', '2000-02-05', NULL);

INSERT INTO ENDERECO
(RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, CEP, PAIS, INSTRUCAO_ENTREGA, ENDERECO_PADRAO, LOGRADOURO,
 USUARIO_ID)
VALUES ('Rua E', '126', 'Apto 505', 'Bairro Oeste', 'São Paulo', 'SP', '05009-000', 'Brasil',
        'Deixar na caixa de correio', TRUE, 'RUA', 1);

INSERT INTO PEDIDO (NOME_USUARIO, TOTAL, VALOR_DESCONTO, VALOR_FRETE, NUM_PARCELA, VALOR_PARCELA, FORMA_PGTO, STATUS, OBSERVACAO, DATA_PEDIDO, DATA_ENTREGA, DATA_PAGAMENTO, DATA_CANCELAMENTO, DATA_ATUALIZACAO, FK_ENDERECO_ENTREGA, ID_CLIENTE)
VALUES ('Cláudio Araújo', 250.00, 10.00, 20.00, 3, 83.33, 'CARTAO_CREDITO', 'Pendente', 'Entrega rápida', NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), NULL, NULL, NOW(), 1, 1);

INSERT INTO ITEM_PEDIDO (QUANTIDADE, PRECO_UNITARIO, VALOR_TOTAL, DESCONTO, VALOR_DESCONTO, VALOR_FRETE, FK_PRODUTO, FK_PEDIDO)
VALUES (1, 2, 100.00, 200.00, 5.0, 10.00, 10.00, 1, 1);

INSERT INTO ITEM_PEDIDO (QUANTIDADE, PRECO_UNITARIO, VALOR_TOTAL, DESCONTO, VALOR_DESCONTO, VALOR_FRETE, FK_PRODUTO, FK_PEDIDO)
VALUES (2, 1, 60.00, 60.00, 0.0, 0.00, 10.00, 2, 1);