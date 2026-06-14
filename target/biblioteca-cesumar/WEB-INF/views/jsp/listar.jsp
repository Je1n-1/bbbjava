<%-- 
    View: listar.jsp
    Responsabilidade: Exibe a tabela com todos os livros cadastrados
    e oferece ação de exclusão por ID.
    Padrão MVC: VIEW — recebe dados do LivroServlet via request attributes.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acervo — Biblioteca Cesumar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<%-- ===== CABEÇALHO ===== --%>
<header>
    <div class="header-inner">
        <span class="header-logo">📚</span>
        <div class="header-texto">
            <h1>Biblioteca Cesumar</h1>
            <p>Sistema de Gerenciamento do Acervo</p>
        </div>
    </div>
</header>

<%-- ===== NAVEGAÇÃO ===== --%>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/livros/listar" class="ativo">📋 Acervo</a></li>
        <li><a href="${pageContext.request.contextPath}/livros/cadastro">➕ Cadastrar Livro</a></li>
        <li><a href="${pageContext.request.contextPath}/faces/cadastro.xhtml">🖊️ Cadastro JSF</a></li>
    </ul>
</nav>

<main>

    <%-- ===== MENSAGEM DE SUCESSO ===== --%>
    <c:if test="${param.sucesso eq 'cadastro'}">
        <div class="alerta alerta-sucesso">
            ✅ Livro cadastrado com sucesso no acervo!
        </div>
    </c:if>

    <c:if test="${param.sucesso eq 'exclusao'}">
        <div class="alerta alerta-sucesso">
            🗑️ Livro removido do acervo com sucesso.
        </div>
    </c:if>

    <%-- ===== MENSAGEM DE ERRO ===== --%>
    <c:if test="${param.erro eq 'nao-encontrado'}">
        <div class="alerta alerta-erro">
            ⚠️ Livro não encontrado. Verifique o ID ou ISBN informado.
        </div>
    </c:if>

    <%-- ===== ESTATÍSTICAS ===== --%>
    <div class="stats-bar">
        <div class="stat-card">
            <div class="stat-numero">${total}</div>
            <div class="stat-label">Livros no Acervo</div>
        </div>
    </div>

    <%-- ===== TABELA DE LIVROS ===== --%>
    <div class="card">
        <div class="card-titulo">📋 Livros Cadastrados</div>

        <c:choose>
            <c:when test="${not empty livros}">
                <div class="tabela-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>Ano</th>
                                <th>ISBN</th>
                                <th>Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="livro" items="${livros}">
                                <tr>
                                    <td data-label="#">${livro.id}</td>
                                    <td data-label="Título"><strong>${livro.titulo}</strong></td>
                                    <td data-label="Autor">${livro.autor}</td>
                                    <td data-label="Ano">${livro.anoPublicacao}</td>
                                    <td data-label="ISBN">
                                        <span class="isbn-badge">${livro.isbn}</span>
                                    </td>
                                    <td data-label="Ação">
                                        <%-- Formulário de exclusão por ID --%>
                                        <form class="form-excluir"
                                              method="post"
                                              action="${pageContext.request.contextPath}/livros/excluir"
                                              onsubmit="return confirm('Deseja remover o livro \'${livro.titulo}\'?');">
                                            <input type="hidden" name="id" value="${livro.id}">
                                            <button type="submit" class="btn btn-perigo">🗑️ Excluir</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="sem-livros">
                    <span class="icone">📭</span>
                    Nenhum livro cadastrado no acervo ainda.
                    <br><br>
                    <a href="${pageContext.request.contextPath}/livros/cadastro" class="btn btn-primario">
                        ➕ Cadastrar primeiro livro
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- ===== EXCLUSÃO MANUAL POR ISBN ===== --%>
    <div class="card">
        <div class="card-titulo">🔍 Excluir por ISBN</div>
        <form method="post" action="${pageContext.request.contextPath}/livros/excluir"
              onsubmit="return confirm('Confirma a exclusão do livro com este ISBN?');">
            <div class="form-grid">
                <div class="campo">
                    <label for="isbn">ISBN do livro<span class="obrigatorio">*</span></label>
                    <input type="text" id="isbn" name="isbn"
                           placeholder="Ex.: 9780132350884"
                           maxlength="17">
                    <span class="dica">Informe o ISBN com 10 ou 13 dígitos.</span>
                </div>
            </div>
            <div class="form-acoes">
                <button type="submit" class="btn btn-perigo">🗑️ Excluir por ISBN</button>
            </div>
        </form>
    </div>

</main>

<footer>
    <p><strong>Biblioteca Cesumar</strong> — Sistema de Gerenciamento do Acervo &copy; 2025</p>
</footer>

</body>
</html>
