package com.example.ServicioAutoPartes.dtos;

public class ModeloDTO {
    private Long id;
    private String nombre;
    private String anio;
    private MarcaDTO marca;

    public ModeloDTO() {
    }

    public ModeloDTO(Long id, String nombre, String anio, MarcaDTO marca) {
        this.id = id;
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

    public MarcaDTO getMarca() {
        return marca;
    }

    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }
}
