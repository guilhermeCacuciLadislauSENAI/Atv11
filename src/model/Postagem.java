package model;

import java.time.LocalDate;

public class Postagem {
    private int id;
    private int usuarioId;
    private String conteudo;
    private LocalDate dataPostagem;

    // Construtores, Getters e Setters
    public Postagem() {
    }

    public Postagem(int id, int usuarioId, String conteudo, LocalDate dataPostagem) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.conteudo = conteudo;
        this.dataPostagem = dataPostagem;
    }

    // Construtor para criar Postagem
    public Postagem(int usuarioId, String conteudo) {
        this.usuarioId = usuarioId;
        this.conteudo = conteudo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(LocalDate dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}