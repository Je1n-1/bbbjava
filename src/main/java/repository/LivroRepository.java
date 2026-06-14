package repository;

import model.Livro;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Repositório responsável pelo armazenamento em memória dos livros.
 * Implementado como Singleton para manter o estado durante a sessão da aplicação.
 * Em um sistema real, esta camada seria substituída por acesso a banco de dados.
 */
public class LivroRepository {

    // Instância única (Singleton)
    private static LivroRepository instancia;

    // Lista interna de livros
    private final List<Livro> livros = new ArrayList<>();

    // Contador de IDs auto-incrementado (thread-safe)
    private final AtomicInteger contadorId = new AtomicInteger(1);

    // Construtor privado - impede instanciação externa
    private LivroRepository() {
        // Dados iniciais de exemplo para facilitar os testes
        adicionar(new Livro(contadorId.getAndIncrement(), "Clean Code", "Robert C. Martin", 2008, "9780132350884"));
        adicionar(new Livro(contadorId.getAndIncrement(), "Design Patterns", "Gang of Four", 1994, "9780201633610"));
        adicionar(new Livro(contadorId.getAndIncrement(), "O Senhor dos Anéis", "J.R.R. Tolkien", 1954, "9780261102354"));
    }

    /**
     * Retorna a instância única do repositório.
     */
    public static synchronized LivroRepository getInstance() {
        if (instancia == null) {
            instancia = new LivroRepository();
        }
        return instancia;
    }

    /**
     * Adiciona um livro à lista (atribuindo ID automático se necessário).
     */
    public void adicionar(Livro livro) {
        if (livro.getId() == 0) {
            livro.setId(contadorId.getAndIncrement());
        }
        livros.add(livro);
    }

    /**
     * Retorna todos os livros cadastrados.
     */
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros); // Retorna cópia defensiva
    }

    /**
     * Remove um livro pelo seu ID numérico.
     * @return true se removido com sucesso, false caso não encontrado
     */
    public boolean removerPorId(int id) {
        return livros.removeIf(l -> l.getId() == id);
    }

    /**
     * Remove um livro pelo ISBN (sem considerar hífens).
     * @return true se removido com sucesso, false caso não encontrado
     */
    public boolean removerPorIsbn(String isbn) {
        String isbnLimpo = isbn.replaceAll("[-\\s]", "");
        return livros.removeIf(l -> l.getIsbn().replaceAll("[-\\s]", "").equals(isbnLimpo));
    }

    /**
     * Verifica se já existe livro com o ISBN informado.
     */
    public boolean isbnExiste(String isbn) {
        String isbnLimpo = isbn.replaceAll("[-\\s]", "");
        return livros.stream().anyMatch(l -> l.getIsbn().replaceAll("[-\\s]", "").equals(isbnLimpo));
    }

    /**
     * Retorna a quantidade total de livros cadastrados.
     */
    public int total() {
        return livros.size();
    }
}
