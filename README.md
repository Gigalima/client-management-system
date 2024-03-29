# Mini Sistema de Gerenciamento de Clientes

Sistema desenvolvido com o objetivo de realizar operações básicas de CRUD (Create, Read, Update, Delete) utilizando Spring Boot para o back-end e Thymeleaf, Bootstrap e jQuery para o front-end. Além disso, são aplicados testes unitários para garantir a qualidade do código. O armazenamento dos dados é realizado em um banco de dados H2.

## Operações

- **Cadastrar Cliente**: Adicionar um novo cliente ao sistema com validação de campos.
- **Listar Clientes**: Exibir todos os clientes, com opção de filtrar por nome, cpf e status (ativo/inativo).
- **Atualizar Cliente**: Modificar informações de um cliente existente, com campo de auditoria da data da última alteração.
- **Deletar Cliente**: Realizar exclusão lógica de um cliente (soft delete).

## Tecnologias Utilizadas

- **Back-end**: Spring Boot
- **Front-end**: Thymeleaf, Bootstrap, jQuery
- **Banco de Dados**: H2
- **Testes Unitários**: JUnit, Mockito

## Pré-requisitos

- JDK (Java Development Kit)
- Maven
- IDE (Eclipse, IntelliJ IDEA, etc.)

## Como Executar

1. Clone o repositório para o seu ambiente local.
2. Importe o projeto em sua IDE preferida.
3. Execute o projeto Spring Boot.
4. Acesse o sistema através do navegador web em http://localhost:8080.
