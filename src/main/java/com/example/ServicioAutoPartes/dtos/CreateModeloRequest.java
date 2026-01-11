package com.example.ServicioAutoPartes.dtos;

public class CreateModeloRequest {
    private String nombre;
    private String anio;
    private MarcaIdWrapper marca;

    public static class MarcaIdWrapper {
        private Long id;

        public MarcaIdWrapper() {}

        public MarcaIdWrapper(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public CreateModeloRequest() {}

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

    public MarcaIdWrapper getMarca() {
        return marca;
    }

    public void setMarca(MarcaIdWrapper marca) {
        this.marca = marca;
    }
}
