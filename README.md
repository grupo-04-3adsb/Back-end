# 🚀 API Back-End TC Ateliê

<img src="https://i.pinimg.com/originals/3f/6a/3e/3f6a3e9f2a4e5bfa8599d81fefbc1c03.gif" width="100%" />

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="60" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="60" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="60" />
</div>

---

## ❗ Sobre o Projeto

Esse repositório contém a **API Back-End** do projeto **TC Ateliê**, desenvolvida em **Java com Spring Boot**. Esta API é responsável por gerenciar usuários, produtos, personalizações e pedidos da aplicação. Possui endpoints RESTful e está conectada a um banco de dados relacional.

---

## ⚙️ Tecnologias Utilizadas

- <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="20" /> Java 17
- <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="20" /> Spring Boot
- <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="20" /> MySQL
- Maven

---

## 📦 Como Rodar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/grupo-04-3adsb/Back-end
```

### 2. Acesse o diretório do projeto

```bash
cd Back-end
```

### 3. Configure o banco de dados

- Crie um banco de dados MySQL com nome `atelie_db`
- Altere o arquivo `src/main/resources/application.properties` com as credenciais do seu banco:

```
spring.datasource.url=jdbc:mysql://localhost:3306/atelie_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

```

### 4. Execute o projeto

- Você pode rodar com sua IDE favorita (IntelliJ, Eclipse, VSCode)
- Ou via terminal:

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📂 Estrutura Principal

- `controller`: Endpoints da API
- `service`: Lógica de negócio
- `repository`: Comunicação com o banco de dados
- `model`: Entidades

---

## 📮 Endpoints Exemplos

- `GET /produtos`
- `POST /produtos`
- `PUT /produtos/{id}`
- `DELETE /produtos/{id}`

---

## 👨‍💻 Contribuidores

Feito por [Grupo 04 - 4ADSB](https://github.com/grupo-04-3adsb)
