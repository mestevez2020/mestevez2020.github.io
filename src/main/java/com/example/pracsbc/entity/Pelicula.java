package com.example.pracsbc.entity;

import jakarta.persistence.*;

import java.util.Objects;

import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class Pelicula implements Comparable<Pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private HashSet<String> genre;
    private HashSet<String> director;
    private String description;



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
            this.genre = new HashSet<>();
        this.genre.add(genero);
    }

    public Pelicula( String nombre, String genre, String director, String description) {
        this.id = 0;
        this.name = nombre;
        this.genre = new HashSet<>();
        this.genre.add(genre);
        this.director = new HashSet<>();
        this.director.add(director);

        this.description = description;
    }
    public boolean addGenre(String genre) {
        return this.genre.add(genre);
    }
    public boolean addDirector(String desc) {
        return this.director.add(desc);
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

    public HashSet<String> getGenre() {
        return genre;
    }

    public HashSet<String> getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
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