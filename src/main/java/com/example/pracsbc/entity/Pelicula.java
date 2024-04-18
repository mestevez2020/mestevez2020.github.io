package com.example.pracsbc.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Pelicula implements Comparable<Pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String genre;



    public Pelicula(){

    }
    public Pelicula(Pelicula pel) {
        this.id = pel.id;
        this.name = pel.name;
        this.genre = pel.genre;
    }
    public Pelicula(String nombre, String genero) {
        this.id = 0;
        this.name = nombre;
        this.genre = genero;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public int compareTo(Pelicula pel) {
        return this.name.compareTo(pel.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pel = (Pelicula) o;
        return Objects.equals(name, pel.name);
    }


}