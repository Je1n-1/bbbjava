<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Página não encontrada — Biblioteca Cesumar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<header>
    <div class="header-inner">
        <span class="header-logo">📚</span>
        <div class="header-texto"><h1>Biblioteca Cesumar</h1></div>
    </div>
</header>
<main>
    <div class="card" style="text-align:center; padding: 60px 40px;">
        <div style="font-size: 4rem; margin-bottom: 16px;">🔍</div>
        <h2 style="color: var(--cor-primaria); margin-bottom: 12px;">Página não encontrada (404)</h2>
        <p style="color: var(--cor-texto-leve); margin-bottom: 24px;">
            O recurso solicitado não foi encontrado no servidor.
        </p>
        <a href="${pageContext.request.contextPath}/livros/listar" class="btn btn-primario">
            ← Voltar ao Acervo
        </a>
    </div>
</main>
</body>
</html>
