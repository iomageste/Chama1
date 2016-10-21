package com.example.ufjf.model;

/**
 * Created by eduar on 7/19/2016.
 */
public class Solicitacao {
    private String solicitante_username;
    private String username_busca;
    private boolean aprovado;
    private double latitude;
    private double longitude;

    public Solicitacao() {

    }

    public Solicitacao(String solicitante_username, String username_busca) {
        this.solicitante_username = solicitante_username;
        this.username_busca = username_busca;
    }

    public Solicitacao(String solicitante_username, String username_busca, double latitude, double longitude) {
        this.solicitante_username = solicitante_username;
        this.username_busca = username_busca;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSolicitante_username() {
        return solicitante_username;
    }

    public void setSolicitante_username(String solicitante_username) {
        this.solicitante_username = solicitante_username;
    }

    public String getUsername_busca() {
        return username_busca;
    }

    public void setUsername_busca(String username_busca) {
        this.username_busca = username_busca;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
