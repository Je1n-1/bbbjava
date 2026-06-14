package model;

/**
 * Classe de modelo que representa um livro no acervo da biblioteca.
 * Contém os atributos principais e métodos de validação.
 */
public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String isbn;

    // Construtor padrão
    public Livro() {}

    // Construtor completo
    public Livro(int id, String titulo, String autor, int anoPublicacao, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    /**
     * Valida se o ISBN possui formato válido (10 ou 13 dígitos numéricos).
     * Remove hífens antes de validar.
     */
    public static boolean isbnValido(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) return false;
        String limpo = isbn.replaceAll("[-\\s]", "");
        return limpo.matches("\\d{10}") || limpo.matches("\\d{13}");
    }

    /**
     * Valida se o ano de publicação é plausível (entre 1000 e o ano atual).
     */
    public static boolean anoValido(int ano) {
        int anoAtual = java.time.Year.now().getValue();
        return ano >= 1000 && ano <= anoAtual;
    }

    @Override
    public String toString() {
        return "Livro{id=" + id + ", titulo='" + titulo + "', autor='" + autor +
               "', ano=" + anoPublicacao + ", isbn='" + isbn + "'}";
    }
}
