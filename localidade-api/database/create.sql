create table estado (id int8 not null, nome varchar(255) not null, sigla varchar(2) not null, primary key (id));
create table municipio (id int8 not null, nome varchar(255) not null, estado_id int8 not null, primary key (id));
alter table if exists municipio add constraint municipio_estado_id_fk foreign key (estado_id) references estado;
