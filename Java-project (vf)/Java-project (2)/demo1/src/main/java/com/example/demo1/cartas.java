package com.example.demo1;

import java.sql.Timestamp;

public class cartas {
    private int id;
    private int idEstado;
    private int idUsuario;
    private String hash;
    private Timestamp fechaEnvio;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;

    public cartas() {
        // Constructor vacío (puedes agregar más constructores según tus necesidades)
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", idEstado=" + idEstado +
                ", idUsuario=" + idUsuario +
                ", hash='" + hash + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}