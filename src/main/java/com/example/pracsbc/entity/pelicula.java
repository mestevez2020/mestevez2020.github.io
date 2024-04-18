package com.example.pracsbc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class pelicula implements Comparable<pelicula> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;



    public pelicula(){

    }
    public pelicula(pelicula pel) {
        this.id = pel.id;
        this.name = pel.name;
    }
    public pelicula(String nombre) {
        this.id = 0;
        this.name = nombre;
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

    @Override
    public int compareTo(pelicula pel) {
        return this.name.compareTo(pel.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        pelicula pel = (pelicula) o;
        return Objects.equals(name, pel.name);
    }


}


