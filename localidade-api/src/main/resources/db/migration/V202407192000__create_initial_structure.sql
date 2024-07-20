-- create sequence bairro_id_seq start 1 increment 1;
-- create sequence endereco_id_seq start 1 increment 1;
-- create sequence logradouro_id_seq start 1 increment 1;

create table estado (
  id int8 not null,
  nome varchar(255) not null,
  sigla varchar(2) not null,
  primary key (id)
);

create table municipio (
  id int8 not null,
  nome varchar(255) not null,
  estado_id int8 not null,
  primary key (id)
);

create table bairro (
  id BIGSERIAL not null,
  descricao varchar(255) not null,
  municipio_id int8 not null,
  primary key (id)
);

create table logradouro (
  id BIGSERIAL not null,
  descricao varchar(255) not null,
  municipio_id int8 not null,
  primary key (id)
);

create table endereco (
  id BIGSERIAL not null,
  cep varchar(8) not null,
  municipio_id int8 not null,
  bairro varchar(255) not null,
  logradouro varchar(255) not null,
  numero int4,
  complemento varchar(255),
  primary key (id)
);

alter table  if exists bairro
add constraint bairro_municipio_id_fk foreign key (municipio_id) references municipio;

alter table if exists endereco
add constraint endereco_municipio_id_fk foreign key (municipio_id) references municipio;

alter table if exists logradouro
add  constraint logradouro_municipio_id_fk foreign key (municipio_id) references municipio;

alter table if exists municipio
add constraint municipio_estado_id_fk foreign key (estado_id) references estado;
