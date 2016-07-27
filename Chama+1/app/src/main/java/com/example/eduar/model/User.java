package com.example.eduar.model;



/**
 * Created by eduar on 7/19/2016.
 */
public class User{

    private String username;
    private String nome;
    private String password;
    private String telefone;
    private boolean notificacoes;
    private int areaBusca;

    public User() {

    }

    public User(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public User(String username, String nome, String password, String telefone, int areaBusca) {
        this.username = username;
        this.nome = nome;
        this.password = password;
        this.telefone = telefone;
        this.areaBusca = areaBusca;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isNotificacoes() { return notificacoes; }

    public void setNotificacoes(boolean notificacoes) { this.notificacoes = notificacoes; }

    public int getAreaBusca() {
        return areaBusca;
    }

    public void setAreaBusca(int areaBusca) {
        this.areaBusca = areaBusca;
    }

}
