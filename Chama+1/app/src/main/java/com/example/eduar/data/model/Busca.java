package com.example.eduar.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igorm on 27/07/2016.
 */
public class Busca extends RealmObject implements Parcelable {

    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String USUARIOS_FALTANDO = "usuarios_faltando";
    private static final String LAT = "latitude";
    private static final String LNG = "longitude";
    private static final String USER = "user";

    @PrimaryKey
    @SerializedName(ID)
    private int id;

    @SerializedName(USER_ID)
    private int user_id;

    @SerializedName(USUARIOS_FALTANDO)
    private int usuarios_faltando;

    @SerializedName(LAT)
    private double latitude;

    @SerializedName(LNG)
    private double longitude;

    @SerializedName(USER)
    private User user;

    public Busca() {
        user = new User();
    }

    protected Busca(Parcel in) {
        this();
        readFromParcel(in);
    }

    public static final Creator<Busca> CREATOR = new Creator<Busca>() {
        @Override
        public Busca createFromParcel(Parcel in) {
            return new Busca(in);
        }

        @Override
        public Busca[] newArray(int size) {
            return new Busca[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUsuarios_faltando() {
        return usuarios_faltando;
    }

    public void setUsuarios_faltando(int usuarios_faltando) {
        this.usuarios_faltando = usuarios_faltando;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        usuarios_faltando = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(user_id);
        parcel.writeInt(usuarios_faltando);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeParcelable(user, i);
    }
}
