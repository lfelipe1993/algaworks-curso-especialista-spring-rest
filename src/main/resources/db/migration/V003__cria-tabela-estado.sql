CREATE TABLE estado(
id bigint not null auto_increment,
nome varchar(80) not null,

primary key(id)
)engine=InnoDB default charset=utf8;

INSERT INTO estado (nome)
SELECT distinct nome_estado from cidade;

ALTER TABLE cidade add column estado_id bigint not null;

update cidade C 
INNER JOIN Estado E ON C.nome_estado = E.nome
set c.estado_id = E.id ;

ALTER TABLE cidade add constraint fk_cidade_estado 
foreign key (estado_id) references estado (id);

alter table cidade drop column nome_estado;
alter table cidade change nome_cidade nome varchar(80) not null;