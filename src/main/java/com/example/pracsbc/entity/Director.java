package com.example.pracsbc.entity;

import jakarta.persistence.*;

import java.util.Objects;

import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class Director implements Comparable<Pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String fecha;
    private HashSet<String> peliculas;

    public Director(String name, String fecha, String peli){
        this.name=name;
        this.fecha=fecha;
        peliculas=new HashSet<>();
        peliculas.add(peli);
    }

    public Director() {

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director dir = (Director) o;
        return Objects.equals(name, dir.name);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean addPelicula(String desc) {
        return this.peliculas.add(desc);
    }


    @Override
    public int compareTo(Pelicula o) {
        return 0;
    }
}