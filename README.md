# Project 107 - Localidades

Projeto para teste na utilização das APIs de Localidades do IBGE e ViaCep

## Docker

Criar network

```bash
docker network create project107-net
```

Gerar imagem Docker, usando o comando:

```bash
mvn package -DskipTests
```

Construir imagem `localidade-api`:

```bash
docker build -t project107/localidade-api .
```

Subir um container:

```bash
docker run -i --rm -p 8090:8080 --network project107-net project107/localidade-api
```

Subir stack

```bash
docker-compose up --build -d -t project107-stack
```
