# Project 107 - Localidades

Projeto para teste na utilização das APIs de Localidades do IBGE e ViaCep

<br>

## Pré-requisitos

- Java 11
- Apache Maven 3.6.3
- Quarkus 2.12.2

<br>

## Executar o Projeto

Comando:

```bash
mvn quarkus:dev
```

<br>

## Documentação da API

Acesse o Swagger pelo endpoint `/swagger`:

http://localhost:8080/swagger

<br>

## Docker

Criar network

```bash
docker network create project107-net
```

### Build de `localidade-api`

Acessar o diretório

```bash
cd localidade-api
```

Gerar o pacote

```bash
mvn package -DskipTests
```

Gerar imagem Docker de `localidade-api`, usando o comando:

```bash
docker build -t project107/localidade-api .
```

Subir stack

```bash
docker-compose up --build -d
```

Extra:

Subir um container separado:

```bash
docker run -i --rm -p 8090:8080 --network project107-net project107/localidade-api
```
