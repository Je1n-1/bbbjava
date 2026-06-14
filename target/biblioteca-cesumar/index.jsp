<%--
    index.jsp — Página inicial da aplicação.
    Redireciona automaticamente para a listagem de livros.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redirecionamento imediato para o Controller principal
    response.sendRedirect(request.getContextPath() + "/livros/listar");
%>
