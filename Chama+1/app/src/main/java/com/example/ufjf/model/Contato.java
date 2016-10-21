package com.example.ufjf.model;

/**
 * Created by eduar on 7/28/2016.
 */
public class Contato {

    private String username;
    private String contato;

    public Contato() {
    }

    public Contato(String username, String contato) {
        this.username = username;
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getUsername() {  return username; }

    public void setUsername(String username) {
        this.username = username;
    }
}
