package model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataCadastro;

    public Usuario(String nome, String email, String senha, LocalDate dataCadastro){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }
    public Usuario(int id, String nome, String email, String senha, LocalDate dataCadastro){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    // métodos getters
    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email;
    }
    public String getSenha(){
        return senha;
    }
    public LocalDate getDataCadastro(){
        return dataCadastro;
    }

    // métodos setters
    public void setId(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

}
