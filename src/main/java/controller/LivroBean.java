package controller;

import model.Livro;
import repository.LivroRepository;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean JSF para cadastro e listagem de livros.
 */
@Named("livroBean")
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LivroRepository repository = LivroRepository.getInstance();

    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String isbn;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public List<Livro> getLivros() { return repository.listarTodos(); }
    public int getTotal() { return repository.total(); }

    public String cadastrar() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        boolean valido = true;

        if (titulo == null || titulo.trim().isEmpty()) {
            ctx.addMessage("formCadastro:titulo",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Título obrigatório", null));
            valido = false;
        }
        if (autor == null || autor.trim().isEmpty()) {
            ctx.addMessage("formCadastro:autor",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autor obrigatório", null));
            valido = false;
        }
        if (!Livro.anoValido(anoPublicacao)) {
            ctx.addMessage("formCadastro:ano",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ano inválido (entre 1000 e o ano atual)", null));
            valido = false;
        }
        if (isbn == null || !Livro.isbnValido(isbn)) {
            ctx.addMessage("formCadastro:isbn",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN inválido (10 ou 13 dígitos)", null));
            valido = false;
        } else if (repository.isbnExiste(isbn)) {
            ctx.addMessage("formCadastro:isbn",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN já cadastrado", null));
            valido = false;
        }

        if (!valido) return null;

        Livro livro = new Livro(0, titulo.trim(), autor.trim(),
                                anoPublicacao, isbn.replaceAll("[-\\s]", ""));
        repository.adicionar(livro);

        ctx.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Livro \"" + titulo + "\" cadastrado com sucesso!", null));

        limparFormulario();
        return null; // Permanece na mesma página
    }

    public String excluir(int id) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        boolean removido = repository.removerPorId(id);
        if (removido) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Livro removido com sucesso.", null));
        } else {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Livro não encontrado.", null));
        }
        return null;
    }

    public void limparFormulario() {
        titulo = null;
        autor = null;
        anoPublicacao = 0;
        isbn = null;
    }
}
