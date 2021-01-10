insert into categoria (id, nome)
values (1, 'informatica');
insert into categoria (id, nome)
values (2, 'escritorio');

insert into produto (id, nome, preco)
values (1, 'computador', 2000);
insert into produto (id, nome, preco)
values (2, 'impressora', 800);
insert into produto (id, nome, preco)
values (3, 'mouse', 80);

insert into produto_categoria (produto_id, categoria_id)
values (1,1);
insert into produto_categoria (produto_id, categoria_id)
values (2,1);
insert into produto_categoria (produto_id, categoria_id)
values (2,2);
insert into produto_categoria (produto_id, categoria_id)
values (3,1);	