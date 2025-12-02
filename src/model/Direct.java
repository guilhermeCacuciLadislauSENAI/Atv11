package model;

import java.time.LocalDate;

public class Direct {
    private int id;
    private int remetenteId;
    private int destinatarioId;
    private String mensagem;
    private LocalDate dataEnvio;

    // Construtores, Getters e Setters
    public Direct() {
    }

    public Direct(int id, int remetenteId, int destinatarioId, String mensagem, LocalDate dataEnvio) {
        this.id = id;
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    // Construtor para enviar mensagem
    public Direct(int remetenteId, int destinatarioId, String mensagem) {
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.mensagem = mensagem;
    }


    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(int remetenteId) {
        this.remetenteId = remetenteId;
    }

    public int getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(int destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}