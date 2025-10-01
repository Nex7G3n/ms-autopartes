package com.example.ServicioAutoPartes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "piezas")
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "pieza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pieza")
    private List<Autoparte> autopartes;

    public Pieza() {
    }

    public Pieza(String nombre, String categoria, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Autoparte> getAutopartes() {
        return autopartes;
    }

    public void setAutopartes(List<Autoparte> autopartes) {
        this.autopartes = autopartes;
    }
}
