# 🏢 Projeto Empresa

Este é um sistema de gerenciamento de funcionários e projetos desenvolvido em Java, utilizando JDBC para integração com banco de dados MySQL.

## 📌 Funcionalidades

- Cadastro, leitura, atualização e remoção de:
  - Pessoas
  - Funcionários
  - Projetos
- Associação de funcionários a projetos
- Regras de negócio implementadas para garantir integridade nas operações
- Conexão com banco de dados MySQL via JDBC

## 🚀 Tecnologias Utilizadas

- Java (JDK 8+)
- JDBC
- MySQL
- Eclipse (estrutura do projeto baseada no Eclipse)
- Git (controle de versão)

## 🧠 Estrutura do Projeto

```
ProjetoEmpresa-main/
├── Principal.java                  # Classe principal do projeto
├── banco_de_dados.sql             # Script de criação do banco de dados
├── mysql-connector-j-9.1.0.jar    # Driver JDBC do MySQL
├── .classpath / .project          # Arquivos de configuração do Eclipse
└── .git/                          # Repositório Git do projeto
```

## ⚙️ Como Executar

1. **Clone o projeto**
   ```bash
   git clone <url-do-repo>
   cd ProjetoEmpresa-main
   ```

2. **Importe no Eclipse**
   - Vá em `File > Import > Existing Projects into Workspace`
   - Selecione a pasta do projeto

3. **Configure o Banco de Dados**
   - Crie um banco MySQL chamado `empresa`
   - Execute o script `banco_de_dados.sql` para criar as tabelas necessárias

4. **Adicione o driver JDBC**
   - No Eclipse, clique com o botão direito no projeto > `Build Path > Configure Build Path`
   - Adicione o `mysql-connector-j-9.1.0.jar` como biblioteca externa

5. **Execute o projeto**
   - Rode a classe `Principal.java`

## 🛠 Requisitos

- Java JDK 8 ou superior
- MySQL
- Eclipse IDE (recomendado)

## 📄 Licença

Este projeto é de uso acadêmico e livre para modificações para fins educacionais.
