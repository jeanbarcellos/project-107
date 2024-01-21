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
