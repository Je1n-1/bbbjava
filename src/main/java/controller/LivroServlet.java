package controller;

import model.Livro;
import repository.LivroRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet principal que atua como Controller no padrão MVC.
 * Recebe todas as requisições, processa a lógica de negócio e
 * direciona para as views (JSP) correspondentes.
 *
 * Mapeamento: /livros/*
 *   GET  /livros/listar   → exibe a listagem de livros
 *   GET  /livros/cadastro → exibe o formulário de cadastro
 *   POST /livros/salvar   → processa o cadastro de um livro
 *   POST /livros/excluir  → processa a exclusão de um livro
 */
@WebServlet(name = "LivroServlet", urlPatterns = {"/livros/*"})
public class LivroServlet extends HttpServlet {

    // Repositório de livros (acesso à camada de dados)
    private final LivroRepository repository = LivroRepository.getInstance();

    /**
     * Trata requisições GET — exibição de páginas.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Obtém a ação a partir da URL (/livros/ACAO)
        String acao = obterAcao(request);

        switch (acao) {
            case "listar":
                listarLivros(request, response);
                break;
            case "cadastro":
                exibirFormularioCadastro(request, response);
                break;
            default:
                // Redireciona para a listagem como padrão
                response.sendRedirect(request.getContextPath() + "/livros/listar");
        }
    }

    /**
     * Trata requisições POST — submissão de formulários.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String acao = obterAcao(request);

        switch (acao) {
            case "salvar":
                salvarLivro(request, response);
                break;
            case "excluir":
                excluirLivro(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/livros/listar");
        }
    }

    // =========================================================================
    // Métodos de ação
    // =========================================================================

    /**
     * Lista todos os livros e encaminha para a view de listagem.
     */
    private void listarLivros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Livro> livros = repository.listarTodos();
        request.setAttribute("livros", livros);
        request.setAttribute("total", repository.total());
        encaminhar(request, response, "/WEB-INF/views/jsp/listar.jsp");
    }

    /**
     * Exibe o formulário de cadastro de livro.
     */
    private void exibirFormularioCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        encaminhar(request, response, "/WEB-INF/views/jsp/cadastro.jsp");
    }

    /**
     * Processa o cadastro de um novo livro com validações completas.
     */
    private void salvarLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura e limpeza dos parâmetros do formulário
        String titulo = limpar(request.getParameter("titulo"));
        String autor  = limpar(request.getParameter("autor"));
        String anoStr = limpar(request.getParameter("anoPublicacao"));
        String isbn   = limpar(request.getParameter("isbn"));

        StringBuilder erros = new StringBuilder();

        // Validação: campos obrigatórios
        if (titulo.isEmpty())  erros.append("O título é obrigatório. ");
        if (autor.isEmpty())   erros.append("O autor é obrigatório. ");
        if (anoStr.isEmpty())  erros.append("O ano de publicação é obrigatório. ");
        if (isbn.isEmpty())    erros.append("O ISBN é obrigatório. ");

        // Validação do ISBN
        if (!isbn.isEmpty() && !Livro.isbnValido(isbn)) {
            erros.append("ISBN inválido (deve ter 10 ou 13 dígitos numéricos). ");
        }

        // Verificação de ISBN duplicado
        if (!isbn.isEmpty() && Livro.isbnValido(isbn) && repository.isbnExiste(isbn)) {
            erros.append("Já existe um livro cadastrado com este ISBN. ");
        }

        // Validação do ano
        int ano = 0;
        if (!anoStr.isEmpty()) {
            try {
                ano = Integer.parseInt(anoStr);
                if (!Livro.anoValido(ano)) {
                    erros.append("Ano de publicação inválido (entre 1000 e o ano atual). ");
                }
            } catch (NumberFormatException e) {
                erros.append("Ano de publicação deve ser um número inteiro. ");
            }
        }

        // Se houver erros, retorna ao formulário com mensagens
        if (erros.length() > 0) {
            request.setAttribute("erro", erros.toString().trim());
            request.setAttribute("titulo", titulo);
            request.setAttribute("autor", autor);
            request.setAttribute("anoPublicacao", anoStr);
            request.setAttribute("isbn", isbn);
            encaminhar(request, response, "/WEB-INF/views/jsp/cadastro.jsp");
            return;
        }

        // Criação e persistência do livro
        Livro livro = new Livro(0, titulo, autor, ano, isbn.replaceAll("[-\\s]", ""));
        repository.adicionar(livro);

        // Redireciona com mensagem de sucesso (Post-Redirect-Get)
        response.sendRedirect(request.getContextPath() + "/livros/listar?sucesso=cadastro");
    }

    /**
     * Processa a exclusão de um livro por ID ou ISBN.
     */
    private void excluirLivro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = limpar(request.getParameter("id"));
        String isbn  = limpar(request.getParameter("isbn"));
        boolean removido = false;

        // Tenta remover por ID primeiro
        if (!idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                removido = repository.removerPorId(id);
            } catch (NumberFormatException e) {
                // ID inválido, ignora e tenta por ISBN
            }
        }

        // Se não removeu por ID, tenta por ISBN
        if (!removido && !isbn.isEmpty()) {
            removido = repository.removerPorIsbn(isbn);
        }

        // Redireciona com resultado da operação
        String param = removido ? "sucesso=exclusao" : "erro=nao-encontrado";
        response.sendRedirect(request.getContextPath() + "/livros/listar?" + param);
    }

    // =========================================================================
    // Métodos utilitários
    // =========================================================================

    /**
     * Extrai a ação da URL (último segmento do pathInfo).
     */
    private String obterAcao(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) return "listar";
        return pathInfo.substring(1); // Remove a barra inicial
    }

    /**
     * Limpa e normaliza uma string de parâmetro (null-safe).
     */
    private String limpar(String valor) {
        return (valor != null) ? valor.trim() : "";
    }

    /**
     * Encaminha a requisição para uma view JSP.
     */
    private void encaminhar(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        request.getRequestDispatcher(view).forward(request, response);
    }
}
