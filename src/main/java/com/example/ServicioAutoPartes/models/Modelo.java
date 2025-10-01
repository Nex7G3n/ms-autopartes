package com.example.ServicioAutoPartes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "anio", length = 4)
    private String anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    @JsonIgnoreProperties("modelos")
    private Marca marca;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("modelo")
    private List<Autoparte> autopartes;

    public Modelo() {
    }

    public Modelo(String nombre, String anio, Marca marca) {
        this.nombre = nombre;
        this.anio = anio;
        this.marca = marca;
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

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Autoparte> getAutopartes() {
        return autopartes;
    }

    public void setAutopartes(List<Autoparte> autopartes) {
        this.autopartes = autopartes;
    }
}
