package com.example.ufjf.model;

/**
 * Created by eduar on 7/27/2016.
 */
public class Busca {

    private String username;
    private int usuariosFaltando;
    private String tipo;
    private int areaBusca;
    private double latitude;
    private double longitude;

    public Busca() {
    }

    public Busca(String username, int usuariosFaltando) {
        this.username = username;
        this.usuariosFaltando = usuariosFaltando;
    }

    public Busca(String username, int usuariosFaltando, String tipo, int areaBusca, double latitude, double longitude) {
        this.username = username;
        this.usuariosFaltando = usuariosFaltando;
        this.tipo = tipo;
        this.areaBusca = areaBusca;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsuariosFaltando() {
        return usuariosFaltando;
    }

    public void setUsuariosFaltando(int usuariosFaltando) {
        this.usuariosFaltando = usuariosFaltando;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAreaBusca() {
        return areaBusca;
    }

    public void setAreaBusca(int areaBusca) {
        this.areaBusca = areaBusca;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
