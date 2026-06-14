<%-- 
    View: cadastro.jsp
    Responsabilidade: Formulário de cadastro de novos livros via Servlet.
    Exibe mensagens de erro de validação retornadas pelo LivroServlet.
    Padrão MVC: VIEW — envia dados para o Controller (LivroServlet POST /salvar).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Livro — Biblioteca Cesumar</title>
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
        <li><a href="${pageContext.request.contextPath}/livros/listar">📋 Acervo</a></li>
        <li><a href="${pageContext.request.contextPath}/livros/cadastro" class="ativo">➕ Cadastrar Livro</a></li>
        <li><a href="${pageContext.request.contextPath}/faces/cadastro.xhtml">🖊️ Cadastro JSF</a></li>
    </ul>
</nav>

<main>

    <%-- ===== ALERTA DE ERRO GERAL ===== --%>
    <c:if test="${not empty erro}">
        <div class="alerta alerta-erro">
            ⚠️ <span>${erro}</span>
        </div>
    </c:if>

    <%-- ===== FORMULÁRIO DE CADASTRO ===== --%>
    <div class="card">
        <div class="card-titulo">➕ Cadastrar Novo Livro</div>

        <%-- 
            O formulário envia os dados via POST para o LivroServlet.
            Em caso de erro, os valores são re-exibidos nos campos.
        --%>
        <form method="post" action="${pageContext.request.contextPath}/livros/salvar"
              novalidate>

            <div class="form-grid">

                <%-- Campo: Título --%>
                <div class="campo campo-largo">
                    <label for="titulo">
                        Título <span class="obrigatorio">*</span>
                    </label>
                    <input type="text"
                           id="titulo"
                           name="titulo"
                           value="${not empty titulo ? titulo : ''}"
                           placeholder="Ex.: Clean Code"
                           maxlength="255"
                           class="${not empty erro and titulo eq '' ? 'erro' : ''}">
                </div>

                <%-- Campo: Autor --%>
                <div class="campo campo-largo">
                    <label for="autor">
                        Autor <span class="obrigatorio">*</span>
                    </label>
                    <input type="text"
                           id="autor"
                           name="autor"
                           value="${not empty autor ? autor : ''}"
                           placeholder="Ex.: Robert C. Martin"
                           maxlength="255"
                           class="${not empty erro and autor eq '' ? 'erro' : ''}">
                </div>

                <%-- Campo: Ano de Publicação --%>
                <div class="campo">
                    <label for="anoPublicacao">
                        Ano de Publicação <span class="obrigatorio">*</span>
                    </label>
                    <input type="number"
                           id="anoPublicacao"
                           name="anoPublicacao"
                           value="${not empty anoPublicacao ? anoPublicacao : ''}"
                           placeholder="Ex.: 2008"
                           min="1000"
                           max="2025">
                    <span class="dica">Entre 1000 e o ano atual.</span>
                </div>

                <%-- Campo: ISBN --%>
                <div class="campo">
                    <label for="isbn">
                        ISBN <span class="obrigatorio">*</span>
                    </label>
                    <input type="text"
                           id="isbn"
                           name="isbn"
                           value="${not empty isbn ? isbn : ''}"
                           placeholder="Ex.: 9780132350884"
                           maxlength="17">
                    <span class="dica">ISBN-10 ou ISBN-13 (somente dígitos ou com hífens).</span>
                </div>

            </div>

            <%-- Botões de ação --%>
            <div class="form-acoes">
                <button type="submit" class="btn btn-primario">💾 Cadastrar Livro</button>
                <a href="${pageContext.request.contextPath}/livros/listar"
                   class="btn btn-secundario">← Voltar ao Acervo</a>
            </div>

        </form>
    </div>

    <%-- ===== DICAS DE PREENCHIMENTO ===== --%>
    <div class="card">
        <div class="card-titulo">ℹ️ Instruções de Preenchimento</div>
        <ul style="padding-left: 1.4rem; line-height: 2; font-size: 0.9rem; color: var(--cor-texto-leve);">
            <li>Todos os campos marcados com <strong style="color: var(--cor-perigo);">*</strong> são obrigatórios.</li>
            <li>O ISBN deve conter <strong>10 ou 13 dígitos numéricos</strong> (hífens são aceitos).</li>
            <li>Não é permitido cadastrar dois livros com o mesmo ISBN.</li>
            <li>O ano de publicação deve ser um número entre <strong>1000</strong> e o ano atual.</li>
        </ul>
    </div>

</main>

<footer>
    <p><strong>Biblioteca Cesumar</strong> — Sistema de Gerenciamento do Acervo &copy; 2025</p>
</footer>

</body>
</html>
