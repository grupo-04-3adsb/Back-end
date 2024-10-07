INSERT INTO categoria (nome_categoria)
VALUES ('Papeteria'),
       ('Topo de Bolo'),
       ('Decoração');

INSERT INTO subcategoria (nome_subcategoria, descricao_subcategoria, fk_categoria)
VALUES ('Cadernos', 'Cadernos personalizados para todas as idades', 1),
       ('Agendas', 'Agendas personalizadas para planejamento', 1),
       ('Topos Personalizados', 'Topos de bolo personalizados para festas', 2),
       ('Topos de Bolo para Casamento', 'Topos de bolo específicos para casamentos', 2),
       ('Balões', 'Balões de diferentes formatos e cores para festas', 3),
       ('Faixas Personalizadas', 'Faixas com mensagens personalizadas para eventos', 3);

INSERT INTO material (id_material, nome_material, preco_unitario, estoque)
VALUES (1, 'Papel', 0.20, 5),
       (2, 'Cartolina', 0.50, 10),
       (3, 'Tecido', 1.00, 3),
       (4, 'Fita', 0.75, 4),
       (5, 'Plástico', 0.30, 2);

INSERT INTO produto (nome, preco_venda, descricao, dimensao, desconto, margem_lucro, sku, url_imagem_principal,
                     personalizavel, personalizacao_obrigatoria, fk_categoria, fk_subcategoria)
VALUES ('Caderno Personalizado', 29.90, 'Caderno com capa personalizada e folhas pautadas.', 'A5', 0.10, 0.30,
        'CADERNO-001', 'url/imagem/caderno_personalizado.jpg', true, false, 1, 1),
       ('Agenda Semanal', 39.90, 'Agenda personalizada com divisões semanais.', 'A5', 0.15, 0.25, 'AGENDA-001',
        'url/imagem/agenda_semanal.jpg', true, false, 1, 2),
       ('Topo de Bolo Personalizado', 49.90, 'Topo de bolo com nome e data do evento.', '30cm', 0.20, 0.35, 'TOPO-001',
        'url/imagem/topo_bolo_personalizado.jpg', true, true, 2, 3),
       ('Topo de Bolo Casamento', 69.90, 'Topo de bolo elegante para casamentos.', '30cm', 0.25, 0.40, 'TOPO-002',
        'url/imagem/topo_bolo_casamento.jpg', true, true, 2, 4),
       ('Balão de Festa', 9.90, 'Balão de festa de 30cm, disponível em várias cores.', '30cm', 0.05, 0.10, 'BALAO-001',
        'url/imagem/balao_festa.jpg', false, false, 3, 5),
       ('Faixa Personalizada', 19.90, 'Faixa personalizada com mensagem para festas.', '2m', 0.10, 0.20, 'FAIXA-001',
        'url/imagem/faixa_personalizada.jpg', true, false, 3, 6);

INSERT INTO imagens_produto (url_img_adicional, fk_produto)
VALUES ('url/imagem/adicional_caderno.jpg', 1),
       ('url/imagem/adicional_agenda.jpg', 2),
       ('url/imagem/adicional_topo_bolo.jpg', 3),
       ('url/imagem/adicional_topo_bolo_casamento.jpg', 4),
       ('url/imagem/adicional_balao.jpg', 5),
       ('url/imagem/adicional_faixa.jpg', 6);

INSERT INTO personalizacao (nome_personalizacao, tipo_personalizacao, fk_produto)
VALUES ('Personalização de Capa', 'Texto', 1),
       ('Cores de Capa', 'Cor', 1),
       ('Personalização de Datas', 'Texto', 2),
       ('Design Especial para Casamento', 'Design', 4),
       ('Mensagem Especial', 'Texto', 3),
       ('Mensagem para Faixa', 'Texto', 6);

INSERT INTO opcao_personalizacao (nome_opcao, descricao, acrescimo_opcao, url_img_opcao, fk_personalizacao)
VALUES ('Texto em Ouro', 'Texto na capa em efeito dourado', 5.00, 'url/imagem/texto_ouro.jpg', 1),
       ('Texto em Prata', 'Texto na capa em efeito prateado', 4.00, 'url/imagem/texto_prata.jpg', 1),
       ('Capa de Couro', 'Capa em material de couro sintético', 10.00, 'url/imagem/capa_couro.jpg', 2),
       ('Adesivo de Casamento', 'Adesivo personalizável para casamentos', 3.00, 'url/imagem/adesivo_casamento.jpg', 4),
       ('Balão de Coração', 'Balão em formato de coração', 2.00, 'url/imagem/balao_coracao.jpg', 5),
       ('Faixa com Logo', 'Faixa que inclui o logo do cliente', 8.00, 'url/imagem/faixa_logo.jpg', 6);

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
VALUES ('Cláudio Araújo', 'claudio@gmail.com', '$2b$12$4FM3A0un93R72ieiEddIE.J9hWbrO64j93W4cJZy0jyQcQo2WMFBC', 'ADMIN', '(11) 98765-4321', 'HABILITADO',
        NOW(), NOW(), '123.456.789-09', 1, 'http://img.png', '2005-01-07', NULL);

