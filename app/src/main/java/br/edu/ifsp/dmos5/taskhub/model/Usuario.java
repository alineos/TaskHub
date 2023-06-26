package br.edu.ifsp.dmos5.taskhub.model;

import java.util.Date;

public class Usuario {

    private String username;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){}

    public Usuario(String username, String nome, String email, String senha) {
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.senha = senha;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
