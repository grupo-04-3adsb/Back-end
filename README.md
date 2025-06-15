# 📦 Ecommerce API – Backend

API desenvolvida para um sistema de e-commerce, com suporte a autenticação via token JWT, envio de e-mails, integração com MercadoPago e MelhorEnvio.

---

## 🚀 Setup

### ✅ Pré-Requisitos

- Java 17+
- Maven
- Docker (recomendado com WSL2, se estiver no Windows)

---

## 🔄 Clonar o Projeto

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

---

## ⚙️ Configuração de Variáveis de Ambiente

Use o `application.properties` como referência para configurar as variáveis sensíveis.

```properties
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
mercadopago.access.token=${MP_ACCESS_TOKEN}
melhorenvio.api.token=${MELHORENVIO_TOKEN}
```

---

## 📧 Configuração do E-mail

No arquivo `application.properties`:

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=587
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

⚠️ Recomendado utilizar serviços como [Mailtrap](https://mailtrap.io/) para testes locais.

---

## 💳 Configuração da API do MercadoPago

```properties
mercadopago.access.token=${MP_ACCESS_TOKEN}
```

Você pode gerar o seu token em: [MercadoPago Developers](https://www.mercadopago.com.br/developers/panel/credentials)

---

## 📦 Configuração da API MelhorEnvio

```properties
melhorenvio.api.url=https://www.melhorenvio.com.br/api
melhorenvio.api.token=${MELHORENVIO_TOKEN}
```

Crie seu token em: [MelhorEnvio](https://www.melhorenvio.com.br/)

---

## 🐳 Docker (opcional)

Você pode utilizar Docker para subir dependências como banco de dados, Kafka, etc.

### Exemplo para subir Kafka (caso utilize):

```bash
cd kafka/
docker compose up -d
```

---

## ▶️ Executando a Aplicação

Na sua IDE ou terminal, execute a classe principal:

```java
EcommerceApplication.java
```

---

## 📍 Documentação da API

Uma vez com a aplicação rodando, acesse a documentação interativa:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🛡️ Segurança

A autenticação é feita via JWT Token, com dois tipos:

- **Token de acesso:** expira em 2 horas
- **Token de refresh:** expira em 7 dias
