# OrderFlow

API REST para gerenciamento de pedidos, desenvolvida com Spring Boot como projeto acadêmico.

---

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Entidades e relacionamentos](#entidades-e-relacionamentos)
- [Regras de negócio](#regras-de-negócio)
- [Endpoints da API](#endpoints-da-api)
- [Como executar](#como-executar)
  - [Banco de dados com Docker](#banco-de-dados-com-docker)
  - [Banco de dados local](#banco-de-dados-local)
  - [Configuração do application.properties](#configuração-do-applicationproperties)
  - [Subindo a aplicação](#subindo-a-aplicação)
- [Documentação Swagger](#documentação-swagger)

---

## Sobre o projeto

O OrderFlow é uma API de controle de pedidos que permite cadastrar clientes e produtos, criar pedidos, adicionar ou remover itens e gerenciar o ciclo de vida de cada pedido por meio de um fluxo de status definido.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.14 |
| Spring Data JPA | — |
| Spring Validation | — |
| SQL Server | 2022 |
| Maven | 3.x |
| Swagger / OpenAPI | 2.8.9 |
| Lombok | — |

---

## Estrutura do projeto

```
src/main/java/com/example/demo/
├── controller/        # Camada de entrada HTTP (REST)
│   ├── ClienteController.java
│   ├── ProdutoController.java
│   └── PedidoController.java
├── service/           # Regras de negócio
│   ├── ClienteService.java
│   ├── ProdutoService.java
│   └── PedidoService.java
├── repository/        # Acesso ao banco de dados (Spring Data JPA)
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   ├── PedidoRepository.java
│   └── ItemPedidoRepository.java
├── entity/            # Entidades JPA
│   ├── Cliente.java
│   ├── Produto.java
│   ├── Pedido.java
│   ├── ItemPedido.java
│   └── PedidoStatus.java
├── dto/
│   ├── request/       # Dados de entrada
│   └── response/      # Dados de saída
└── exception/         # Exceções customizadas e handler global
```

---

## Entidades e relacionamentos

```
Cliente ──── faz ────► Pedido ──── contém ────► ItemPedido ──── referencia ────► Produto
```

- Um **Cliente** pode ter zero ou vários **Pedidos**
- Um **Pedido** pertence a exatamente um **Cliente**
- Um **Pedido** contém um ou mais **ItemPedidos**
- Cada **ItemPedido** referencia um **Produto** e registra a quantidade e o subtotal

---

## Regras de negócio

### Clientes
- E-mail deve ser único — não é possível cadastrar dois clientes com o mesmo e-mail
- Não é possível excluir um cliente que possui pedidos vinculados

### Produtos
- Não é possível excluir um produto que está referenciado em algum pedido

### Pedidos
- Um pedido é criado sempre com status `PENDENTE`
- O valor total é calculado automaticamente com base nos itens adicionados
- Itens só podem ser adicionados ou removidos enquanto o pedido estiver com status `PENDENTE`
- O status segue um fluxo de transições definido:

```
PENDENTE ──► CONFIRMADO ──► ENTREGUE
    │              │
    └──────────────┴──► CANCELADO
```

| Transição | Permitida |
|---|---|
| PENDENTE → CONFIRMADO | ✅ |
| PENDENTE → CANCELADO | ✅ |
| CONFIRMADO → ENTREGUE | ✅ |
| CONFIRMADO → CANCELADO | ✅ |
| ENTREGUE → qualquer | ❌ |
| CANCELADO → qualquer | ❌ |

### Validações de entrada
- Nome, e-mail e telefone do cliente são obrigatórios
- Telefone deve conter 10 ou 11 dígitos
- Nome e preço do produto são obrigatórios; preço deve ser maior que zero
- Quantidade de item deve ser pelo menos 1

---

## Endpoints da API

### Clientes — `/clientes`

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/clientes` | Cadastrar cliente |
| GET | `/clientes` | Listar clientes (com paginação e filtro por nome) |
| GET | `/clientes/{id}` | Buscar cliente por ID |
| PUT | `/clientes/{id}` | Atualizar cliente |
| DELETE | `/clientes/{id}` | Remover cliente |

### Produtos — `/produtos`

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar produtos (com paginação e filtro por nome) |
| GET | `/produtos/{id}` | Buscar produto por ID |
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Remover produto |

### Pedidos — `/pedidos`

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/pedidos?clienteId={id}` | Criar pedido para um cliente |
| GET | `/pedidos` | Listar todos os pedidos |
| GET | `/pedidos/{id}` | Buscar pedido por ID |
| POST | `/pedidos/{id}/itens` | Adicionar item ao pedido |
| DELETE | `/pedidos/{pedidoId}/itens/{itemId}` | Remover item do pedido |
| PATCH | `/pedidos/{id}/status` | Atualizar status do pedido |

---

## Como executar

### Pré-requisitos

- Java 21
- Maven 3.x
- Docker (para rodar o banco via container) **ou** SQL Server instalado localmente

---

### Banco de dados com Docker

Execute o comando abaixo para subir um container SQL Server 2022:

```bash
docker run -d \
  --name sqlserver-orderflow \
  -e ACCEPT_EULA=Y \
  -e SA_PASSWORD='OrderFlow@123' \
  -p 1433:1433 \
  mcr.microsoft.com/mssql/server:2022-latest
```

Aguarde alguns segundos e crie o banco de dados:

```bash
docker exec sqlserver-orderflow /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P 'OrderFlow@123' -No \
  -Q "CREATE DATABASE orderflow;"
```

---

### Banco de dados local

Se preferir usar uma instância local do SQL Server, crie o banco manualmente:

```sql
CREATE DATABASE orderflow;
```

---

### Configuração do application.properties

O arquivo de configuração está em `demo/src/main/resources/application.properties`.

**Conexão com Docker (padrão do projeto):**

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=orderflow;encrypt=true;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=sa
spring.datasource.password=OrderFlow@123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Conexão com instância local (ajuste conforme sua configuração):**

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=orderflow;encrypt=false
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> **Observação:** `trustServerCertificate=true` é necessário quando o SQL Server usa certificado autoassinado, como ocorre nos containers Docker. Para instâncias locais com SSL configurado corretamente, pode ser removido.

---

### Subindo a aplicação

Na pasta `demo`, execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

O Hibernate cria as tabelas automaticamente no banco na primeira execução (`ddl-auto=update`).

---

## Documentação Swagger

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Todos os endpoints estão documentados e podem ser testados diretamente pela interface.
