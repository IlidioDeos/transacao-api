# Transação API - Projeto Big Data IBMEC

## 📄 Descrição

A **Transação API** é uma API RESTful desenvolvida em **Spring Boot** que simula operações bancárias básicas, permitindo a criação de contas, autorização de transações e notificação de transações. O projeto foi construído seguindo as melhores práticas de desenvolvimento, garantindo escalabilidade, segurança e facilidade de manutenção.

## 🚀 Funcionalidades

- **Criação de Contas:**
    - Criar uma nova conta bancária com um limite de crédito disponível.
    - Desativar uma conta existente.
    - Consultar detalhes de uma conta por ID.

- **Gerenciamento de Transações:**
    - Autorizar transações para uma conta específica.
    - Notificar o sistema sobre o resultado de uma transação autorizada.

- **Validações Rigorosas:**
    - Verificação de existência de conta.
    - Validação de saldo disponível antes da autorização de transações.
    - Tratamento de exceções com mensagens claras e informativas.

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (Modo arquivo para persistência de dados)
- **JUnit 5** (Testes unitários e de integração)
- **Mockito** (Mocking em testes)
- **Spring Security** (Configuração básica)
- **Maven** (Gerenciamento de dependências)
- **Lombok** (Redução de código boilerplate)

## 📚 Documentação da API

**Endpoints Principais:**

### Account (Conta)

- `POST /api/accounts` - **Criar uma nova conta.**
- `PUT /api/accounts/{accountId}/deactivate` - **Desativar uma conta existente.**
- `GET /api/accounts/{accountId}` - **Obter detalhes de uma conta por ID.**

### Transaction (Transação)

- `POST /api/transactions` - **Autorizar uma nova transação.**
- `POST /api/transactions/{transactionId}/notify` - **Notificar o sistema sobre o resultado de uma transação.**

## Notification (Notificação)

- No terminal aparece uma simulação de notificação.

## 🔧 Como Executar o Projeto

### 📝 Pré-requisitos

- **Java 21** instalado. [Download Java](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- **Maven 3.8.1** ou superior instalado. [Download Maven](https://maven.apache.org/download.cgi)
- **Git** instalado. [Download Git](https://git-scm.com/downloads)

### 📦 Clonando o Repositório

```bash
git clone https://github.com/seu-usuario/transacao-api.git
cd transacao-api
```

### 🛠 Configuração do Banco de Dados

O projeto utiliza o **H2 Database** em modo de arquivo para persistência dos dados. As configurações estão definidas no arquivo `src/main/resources/application.properties`:

```properties
# Configurações do DataSource
spring.datasource.url=jdbc:h2:file:./database
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configurações do JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### Acessando o Banco de Dados

Você pode acessar o banco de dados H2 usando o **H2 Console**:

1. **Habilite o H2 Console** (se ainda não estiver habilitado):

   Adicione as seguintes propriedades ao `application.properties`:

   ```properties
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   ```

2. **Inicie a aplicação**:

   ```bash
   mvn spring-boot:run
   ```

3. **Acesse o H2 Console**:

   Abra o navegador e navegue para `http://localhost:8080/h2-console`.

4. **Configure a conexão**:

    - **JDBC URL:** `jdbc:h2:file:./database`
    - **User Name:** `sa`
    - **Password:** (deixe em branco)

5. **Conecte-se e explore o banco de dados**.

**Nota:** O arquivo do banco de dados (`database.mv.db`) será criado no diretório raiz do projeto.

### 🏃 Executando a Aplicação

#### Usando o Maven

```bash
mvn spring-boot:run
```

#### Empacotando a Aplicação

Para gerar o JAR executável:

```bash
mvn clean install
java -jar target/transacao-api-0.0.1-SNAPSHOT.jar
```

### 🧪 Executando Testes

Para rodar os testes unitários e de integração:

```bash
mvn test
```

Os testes são configurados para usar o perfil `test`, que utiliza o H2 em memória, garantindo isolamento dos dados.

## 📥 Testando a API com o Postman

Você pode utilizar o **Postman** ou outra ferramenta similar para testar os endpoints da API.

### Exemplo de Requisição para Criar uma Nova Conta

- **Endpoint:** `POST /api/accounts`
- **Headers:**
    - `Content-Type: application/json`
- **Body:**

  ```json
    {
    "cardNumber": "1234-5678-9012-3457",
    "availableLimit": 1000
    }
  ```

### Exemplo de Requisição para Autorizar uma Transação

- **Endpoint:** `POST /api/transactions`
- **Headers:**
    - `Content-Type: application/json`
- **Body:**

  ```json
  {
    "cardNumber": "1234-5678-9012-3457",
    "amount": 100.00,
    "merchant": "Mercado Livre"
}

  ```

### Outros Endpoints

- **Desativar Conta:**

    - **PUT /api/accounts/{accountId}/deactivate**

- **Obter Detalhes da Conta:**

    - **GET /api/accounts/{accountId}**

- **Notificar Transação:**

    - **POST /api/transactions/{transactionId}/notify**

**Nota:** Substitua `{accountId}` e `{transactionId}` pelos IDs reais retornados nas respostas das requisições anteriores.

## 🛡 Segurança

A aplicação utiliza **Spring Security** para configurações básicas de segurança.

### 🔐 Configuração Básica de Segurança

Por padrão, todos os endpoints estão acessíveis sem autenticação para facilitar o desenvolvimento e testes iniciais.

#### Ajustando a Segurança

Se desejar proteger os endpoints:

1. **Adicione a dependência do Spring Security no `pom.xml`:**

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   ```

2. **Configure as regras de segurança criando uma classe de configuração, por exemplo, `SecurityConfig.java`.**

**Nota:** Lembre-se de ajustar as configurações de segurança conforme as necessidades do seu projeto, especialmente antes de colocar a aplicação em produção.

