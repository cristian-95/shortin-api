# Shortin API

Uma API simples para encurtar URLs.

## Funcionalidades

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/shorten` | Cria uma URL curta a partir de uma URL longa |
| `GET` | `/{code}` | Redireciona para a URL original (HTTP 302) |
| `DELETE` | `/{code}` | Remove uma URL encurtada |

## Tecnologias Utilizadas

- **Java 17** + **Spring Boot 3.4.5**
- **PostgreSQL**
- **Flyway**
- **Testcontainers**
- **Docker Compose**
- **Lombok**

## Pré‑requisitos

- Java 17+
- Docker e Docker Compose
- Maven

## Como Executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/cristian-95/shortin-api.git
   cd shortin-api
   ```

2. **Configure as variáveis de ambiente** (crie um arquivo `.env` ou defina na IDE):
   ```env
   DB_USER=
   DB_PASSWORD=
   ENCODER_LENGTH=
   ENCODER_ALPHABET=
   ENCODER_ALGORITHM=
   ENCODER_BASE=
   ```

3. **Suba o banco de dados com Docker Compose:**
   ```bash
   docker-compose up -d
   ```

4. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev   
   ```

A API estará disponível em `http://localhost:8080`.

## Endpoints da API

### Criar URL curta
```http
POST /shorten
Content-Type: application/json

{
  "url": "https://exemplo.com/artigo"
}
```
**Resposta (201 Created):**
```json
{
  "longUrl": "https://exemplo.com/artigo",
  "shortUrl": "http://localhost:8080/abc123"
}
```

### Redirecionar para URL original
```http
GET /abc123
```
→ Redireciona para `https://exemplo.com/artigo` (HTTP 302)

### Excluir URL encurtada
```http
DELETE /abc123
```
→ **204 No Content** (sucesso) ou **404 Not Found**

## Testes

O projeto inclui testes unitários (Mockito) e de integração (Testcontainers).

```bash
# Executar todos os testes
./mvnw test
```

Os testes de integração sobem um PostgreSQL real em container, garantindo fidelidade ao ambiente de produção.

## Contribuição

Contribuições são bem‑vindas. Sinta‑se à vontade para abrir issues ou pull requests.

## Licença

Este projeto está licenciado sob a [MIT License](https://github.com/cristian-95/shortin-api/blob/master/LICENSE).