package com.example.pracsbc.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String extension;
    private String pais;

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad(String nombre, String extension, String pais) {
        this.nombre = nombre;
        this.extension = extension;
        this.pais = pais;
    }

    public Ciudad() {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getExtension() {
        return extension;
    }
}
