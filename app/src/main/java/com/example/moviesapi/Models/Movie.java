package com.example.moviesapi.Models;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Serializable {
    private int id;
    private String titulo;
    private int estreno;
    private int valoracion;

    public Movie() {
    }

    public Movie(int id) {
        this.id = id;
    }

    public Movie(String titulo, int estreno, int valoracion) {
        this.titulo = titulo;
        this.estreno = estreno;
        this.valoracion = valoracion;
    }

    public Movie(int id, String titulo, int estreno, int valoracion) {
        this.id = id;
        this.titulo = titulo;
        this.estreno = estreno;
        this.valoracion = valoracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getEstreno() {
        return estreno;
    }

    public void setEstreno(int estreno) {
        this.estreno = estreno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getId() == movie.getId() ||
                Objects.equals(getTitulo().toLowerCase(), movie.getTitulo().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo());
    }

}
