package com.example.pracsbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Objects;

public class Actor implements Comparable<Pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String fechaNaci;
    private HashSet<Pelicula> peliculas;
    private String paisNaci;


    public Actor(String name, String fecha, String paisNaci){
        this.name=name;
        this.fechaNaci=fecha;
        this.paisNaci=paisNaci;
        this.peliculas=new HashSet<>();
    }

    public Actor() {

    }


    public long getId() {
        return id;
    }

    public String getFechaNaci() {
        return fechaNaci;
    }

    public HashSet<Pelicula> getPeliculas() {
        return peliculas;
    }

    public String getPaisNaci() {
        return paisNaci;
    }
    public boolean addPeliculas(Pelicula pelicula){
       return peliculas.add(pelicula);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(fechaNaci, actor.fechaNaci);
    }

    @Override
    public int compareTo(Pelicula o) {
        return 0;
    }
}
