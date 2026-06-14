<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Erro interno — Biblioteca Cesumar</title>
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
        <div style="font-size: 4rem; margin-bottom: 16px;">⚠️</div>
        <h2 style="color: var(--cor-primaria); margin-bottom: 12px;">Erro interno do servidor (500)</h2>
        <p style="color: var(--cor-texto-leve); margin-bottom: 24px;">
            Ocorreu um erro inesperado. Por favor, tente novamente ou contate o suporte.
        </p>
        <a href="${pageContext.request.contextPath}/livros/listar" class="btn btn-primario">
            ← Voltar ao Acervo
        </a>
    </div>
</main>
</body>
</html>
