package com.example.eduar.model;

import io.realm.RealmObject;

/**
 * Created by eduar on 7/19/2016.
 */
public class User extends RealmObject {

    private int id;
    private String nome;
    private String password;
    private String telefone;
    private String email;
    private Boolean notificacoes;
    private int areaBusca;

    public User() {

    }

    public User(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Boolean notificacoes) {
        this.notificacoes = notificacoes;
    }

    public int getAreaBusca() {
        return areaBusca;
    }

    public void setAreaBusca(int areaBusca) {
        this.areaBusca = areaBusca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
