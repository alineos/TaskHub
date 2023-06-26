package br.edu.ifsp.dmos5.taskhub.model;

import java.util.Date;

public class Tarefa {

    private String observacao;
    private String titulo;
    private int prioridade;
    private Date deadline;
    private boolean isDomestica;

    private boolean isConcluida;



    private String username;



    public Tarefa() {}

    public Tarefa(String observacao, String titulo, int prioridade, Date deadline, boolean isDomestica,String username) {
        this.observacao = observacao;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.deadline = deadline;
        this.isDomestica = isDomestica;
        this.username = username;
        this.isConcluida = false;

    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isDomestica() {
        return isDomestica;
    }

    public void setDomestica(boolean domestica) {
        isDomestica = domestica;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConcluida() {
        return isConcluida;
    }

    public void setConcluida(boolean concluida) {
        isConcluida = concluida;
    }


}
