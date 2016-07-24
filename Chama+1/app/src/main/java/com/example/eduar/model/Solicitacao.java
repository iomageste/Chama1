package com.example.eduar.model;

/**
 * Created by eduar on 7/19/2016.
 */
public class Solicitacao {
    private int solicitante_id;
    private int busca_user_id;
    private boolean aprovado;

    public Solicitacao() {

    }

    public Solicitacao(int solicitante_id, int busca_user_id) {
        this.solicitante_id = solicitante_id;
        this.busca_user_id = busca_user_id;
    }

    public int getSolicitante_id() {
        return solicitante_id;
    }

    public void setSolicitante_id(int solicitante_id) {
        this.solicitante_id = solicitante_id;
    }

    public int getBusca_user_id() {
        return busca_user_id;
    }

    public void setBusca_user_id(int busca_user_id) {
        this.busca_user_id = busca_user_id;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
