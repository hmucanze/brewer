INSERT INTO permissao(id,nome) VALUES(0,"ROLE_CADASTRAR_USUARIO");
INSERT INTO permissao(id,nome) VALUES(0,"ROLE_CADASTRAR_CLIENTE");
INSERT INTO permissao(id,nome) VALUES(0,"ROLE_CADASTRAR_CERVEJA");
INSERT INTO permissao(id,nome) VALUES(0,"ROLE_CADASTRAR_VENDA");

INSERT INTO grupo_permissao(grupo_id,permissao_id) VALUES(1,1);
INSERT INTO grupo_permissao(grupo_id,permissao_id) VALUES(1,2);
INSERT INTO grupo_permissao(grupo_id,permissao_id) VALUES(1,3);
INSERT INTO grupo_permissao(grupo_id,permissao_id) VALUES(1,4);
INSERT INTO grupo_permissao(grupo_id,permissao_id) VALUES(2,4);
