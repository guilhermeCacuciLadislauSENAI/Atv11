package model;

import java.time.LocalDate;

public class Curtida {
    private int id;
    private int postagemId;
    private int usuarioId;
    private LocalDate dataCurtida;

    // Construtores, Getters e Setters
    public Curtida() {
    }

    public Curtida(int id, int postagemId, int usuarioId, LocalDate dataCurtida) {
        this.id = id;
        this.postagemId = postagemId;
        this.usuarioId = usuarioId;
        this.dataCurtida = dataCurtida;
    }

    // Construtor para curtir
    public Curtida(int postagemId, int usuarioId) {
        this.postagemId = postagemId;
        this.usuarioId = usuarioId;
    }


    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostagemId() {
        return postagemId;
    }

    public void setPostagemId(int postagemId) {
        this.postagemId = postagemId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getDataCurtida() {
        return dataCurtida;
    }

    public void setDataCurtida(LocalDate dataCurtida) {
        this.dataCurtida = dataCurtida;
    }
}