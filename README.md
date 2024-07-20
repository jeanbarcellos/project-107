# Project 107 - Localidades

Projeto para teste na utilização das APIs de Localidades do IBGE e ViaCep

## Docker

### **Opção 1 - Apenas rodar projeto | Compose com imagens do Docker HUB**

Rodar o Docker Compose:

```bash
docker-compose up -d
```

- `-d` ou `--detach`: Modo desanexado: execute os contêineres em segundo plano, imprima novos nomes de contêineres.

Verificar status

```bash
docker-compose ps
```

Derrubar compose

```bash
docker-compose down
```

<br>

### **Opção 2 - Desenvolvimento - Apenas os recursos**

Se você deseja apenas levantar o banco de dado, para rodar o `backend` e `frontend` separadamente execute.

```bash
docker-compose -f docker-compose_only-resources.yml up -d
```

Derrubar/baixar containers

```bash
docker-compose down
```

<br>

### **Opção 5 - Manual - Geração das imagens manualmente**

Criar rede:

```bash
docker network create project107_net
```

#### **Database**

Criar volume:

```bash
docker volume create project107_database_data
```

Rodar container:

```bash
docker run -d --rm \
  -p 5532:5432 \
  --network project107_net \
  -v "project107_database_data:/var/lib/postgresql/data" \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=project107_java \
  --name project107_database \
  postgres:14.5
```

#### **Backend**

Acessar diretorio

```bash
cd backend
```

Empacotar o projeto

```bash
mvn clean package -DskipTests
```

Gerar imagem Docker

```bash
docker image build -t jeanbarcellos/project107_backend .
```

_ATENÇÃO:_

Para levantar um container com a imagem recém criada, usando o comando:

```bash
docker run -i --rm \
  -p 8092:8080 \
  --network project107_net \
  -e quarkus.datasource.jdbc.url=jdbc:postgresql://database:5432/localidade \
  --name project107_backend \
  jeanbarcellos/project107_backend
```

- `-i` ou `--interactive`: Mantenha o STDIN aberto mesmo se não estiver conectado
- `--rm`: Remova automaticamente o contêiner quando ele sair
- `-p` ou `--publish`: Definição da porta
- `--name`: Atribuir um nome ao contêiner
- `--network`: Conectar um contêiner a uma rede
- `-v` ou `--volume`: Vincular montar um volume

### **Extra**

Acessar containeres em modo bash

```bash
# Database
docker exec -it project107_database bash

# Backend
docker exec -it project107_backend bash
```
