# 📚 Sistema Web — Biblioteca Cesumar

Sistema web de gerenciamento de acervo desenvolvido em **Java** com **Servlets**, **JSP** e **JSF**, seguindo o padrão arquitetural **MVC**.

---

## 🗂️ Estrutura do Projeto

```
biblioteca-cesumar/
├── pom.xml                                      ← Dependências Maven
├── README.md
└── src/main/
    ├── java/
    │   ├── model/
    │   │   └── Livro.java                       ← Entidade (MODEL)
    │   ├── repository/
    │   │   └── LivroRepository.java             ← Camada de dados
    │   └── controller/
    │       ├── LivroServlet.java                ← Servlet MVC (CONTROLLER)
    │       └── LivroBean.java                   ← Managed Bean JSF (CONTROLLER)
    └── webapp/
        ├── index.jsp                            ← Redirecionamento inicial
        ├── css/
        │   └── estilo.css                       ← Folha de estilos
        ├── faces/
        │   └── cadastro.xhtml                   ← Interface JSF (VIEW)
        └── WEB-INF/
            ├── web.xml                          ← Descritor de implantação
            ├── faces-config.xml                 ← Configuração JSF
            └── views/jsp/
                ├── listar.jsp                   ← Listagem de livros (VIEW)
                ├── cadastro.jsp                 ← Formulário de cadastro (VIEW)
                ├── erro404.jsp                  ← Página de erro 404
                └── erro500.jsp                  ← Página de erro 500
```

---

## 🏗️ Padrão MVC

| Camada       | Arquivo(s)                            | Responsabilidade                              |
|-------------|---------------------------------------|-----------------------------------------------|
| **Model**   | `Livro.java`                          | Entidade com atributos e validações           |
| **Model**   | `LivroRepository.java`                | Armazenamento e consulta dos livros           |
| **Controller** | `LivroServlet.java`               | Processa requisições HTTP (GET/POST)          |
| **Controller** | `LivroBean.java`                  | Managed Bean JSF — ações do formulário JSF    |
| **View**    | `listar.jsp`                          | Exibe tabela de livros (JSP)                  |
| **View**    | `cadastro.jsp`                        | Formulário de cadastro (JSP)                  |
| **View**    | `cadastro.xhtml`                      | Formulário de cadastro e listagem (JSF)       |

---

## ⚙️ Funcionalidades

### ✅ Cadastro de livros
- Formulário disponível em **duas interfaces**: JSP (`/livros/cadastro`) e JSF (`/faces/cadastro.xhtml`)
- Campos: Título, Autor, Ano de Publicação, ISBN

### ✅ Listagem de livros
- Tabela completa com todos os livros cadastrados
- Exibe: ID, Título, Autor, Ano, ISBN
- Contador de livros no acervo

### ✅ Exclusão de livros
- Por **botão na tabela** (por ID — JSP e JSF)
- Por **ISBN** via formulário dedicado na página de listagem
- Confirmação antes de excluir

### ✅ Validações implementadas
| Validação                        | Mensagem de erro                                  |
|---------------------------------|---------------------------------------------------|
| Campos obrigatórios vazios       | "O [campo] é obrigatório."                        |
| ISBN com formato inválido        | "ISBN inválido (deve ter 10 ou 13 dígitos)"       |
| ISBN duplicado                   | "Já existe um livro cadastrado com este ISBN"     |
| Ano de publicação inválido       | "Ano inválido (entre 1000 e o ano atual)"         |
| Ano não numérico                 | "Ano deve ser um número inteiro"                  |

---

## 🚀 Como executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Apache Tomcat 10.1+ **ou** WildFly/Payara (suporte a Jakarta EE 10)

### 1. Compilar e gerar o WAR
```bash
mvn clean package
```

### 2. Implantar no Tomcat
Copie o arquivo `target/biblioteca-cesumar.war` para a pasta `webapps/` do Tomcat e inicie o servidor:
```bash
$TOMCAT_HOME/bin/startup.sh   # Linux/Mac
$TOMCAT_HOME\bin\startup.bat  # Windows
```

### 3. Acessar no navegador
| URL                                                         | Descrição                |
|------------------------------------------------------------|--------------------------|
| `http://localhost:8080/biblioteca-cesumar/`                | Página inicial           |
| `http://localhost:8080/biblioteca-cesumar/livros/listar`   | Listagem (JSP)           |
| `http://localhost:8080/biblioteca-cesumar/livros/cadastro` | Cadastro (JSP)           |
| `http://localhost:8080/biblioteca-cesumar/faces/cadastro.xhtml` | Cadastro (JSF)      |

---

## 🔄 Fluxo de Requisições

```
Usuário → Navegador
    │
    ▼
[index.jsp] ──redirect──► [LivroServlet GET /listar]
                                    │
                          LivroRepository.listarTodos()
                                    │
                          forward► [listar.jsp] ──► Navegador

[listar.jsp / cadastro.jsp]
    │ POST /livros/salvar
    ▼
[LivroServlet POST /salvar]
    │ valida campos
    │ valida ISBN
    │ LivroRepository.adicionar(livro)
    │
    └── redirect (PRG) ──► [GET /listar?sucesso=cadastro]

[faces/cadastro.xhtml]
    │ h:commandButton → livroBean.cadastrar()
    ▼
[LivroBean.cadastrar()]
    │ valida via FacesMessage
    │ LivroRepository.adicionar(livro)
    └── outcome "listar" ──► [faces/cadastro.xhtml] (com mensagem)
```

---

## 📦 Dependências (pom.xml)

| Dependência                     | Versão   | Uso                         |
|--------------------------------|----------|-----------------------------|
| `jakarta.servlet-api`          | 6.0.0    | Servlets (Controller)       |
| `org.glassfish:jakarta.faces`  | 4.0.1    | JSF (Mojarra)               |
| `jakarta.servlet.jsp.jstl-api` | 3.0.0    | Tags JSTL nas JSPs          |
| `jakarta.el-api`               | 5.0.0    | Expression Language         |

---

## 👨‍💻 Autoria
Projeto desenvolvido para a disciplina de Desenvolvimento Web — Universidade Cesumar (Unicesumar).
