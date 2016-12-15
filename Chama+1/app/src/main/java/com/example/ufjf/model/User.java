package com.example.ufjf.model;



/**
 * Created by eduar on 7/19/2016.
 */
public class User {

    private String uid;
    private String username;
    private String email;
    private String telefone;
    private boolean notificacoes;
    private int areaBusca;

    public User() {
        this.areaBusca = 10;
        this.telefone = "(00) 0000-0000";
    }

    public User(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.areaBusca = 10;
        this.telefone = "(00) 0000-0000";
    }

    public User(String username, String telefone) {
        this.username = username;
        this.telefone = telefone;
        this.areaBusca = 10;
        this.telefone = "(00) 0000-0000";
    }

    public User(String uid, String username, String email, String telefone, boolean notificacoes, int areaBusca) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.telefone = telefone;
        this.notificacoes = notificacoes;
        this.areaBusca = areaBusca;
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) { this.email = email; }

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
