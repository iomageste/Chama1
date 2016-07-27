package com.example.eduar.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igorm on 27/07/2016.
 */
public class User extends RealmObject implements Parcelable {

    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String PASSWORD = "password";
    private static final String TELEFONE = "telefone";
    private static final String EMAIL = "email";
    private static final String NOTIFICACOES = "notificacoes";
    private static final String AREABUSCA = "areaBusca";
    public static final String BUSCAS = "buscas";

    @PrimaryKey
    @SerializedName(ID)
    private int id;

    @SerializedName(NOME)
    private String nome;

    @SerializedName(PASSWORD)
    private String password;

    @SerializedName(TELEFONE)
    private String telefone;

    @SerializedName(EMAIL)
    private String email;

    @SerializedName(NOTIFICACOES)
    private boolean notificacoes;

    @SerializedName(AREABUSCA)
    private int areaBusca;

    @SerializedName(BUSCAS)
    private RealmList<Busca> buscas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RealmList<Busca> getBuscas() {
        return buscas;
    }

    public void setBuscas(RealmList<Busca> buscas) {
        this.buscas = buscas;
    }

    public User() {
        buscas = new RealmList<>();
    }

    protected User(Parcel in) {
        this();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.nome = in.readString();
        this.password = in.readString();
        this.telefone = in.readString();
        this.email = in.readString();
        this.notificacoes = in.readInt() == 1;
        this.areaBusca = in.readInt();
        in.readTypedList(buscas, Busca.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(password);
        parcel.writeString(telefone);
        parcel.writeString(email);
        parcel.writeInt(notificacoes ? 1 : 0);
        parcel.writeInt(areaBusca);
        parcel.writeTypedList(buscas);
    }
}
