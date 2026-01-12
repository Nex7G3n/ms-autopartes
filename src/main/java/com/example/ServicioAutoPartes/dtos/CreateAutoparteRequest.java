package com.example.ServicioAutoPartes.dtos;

public class CreateAutoparteRequest {
    private String codigoProducto;
    private ModeloIdWrapper modelo;
    private PiezaIdWrapper pieza;
    private Double precio;
    private Integer stock;
    private String estado;

    public static class ModeloIdWrapper {
        private Long id;

        public ModeloIdWrapper() {}

        public ModeloIdWrapper(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public static class PiezaIdWrapper {
        private Long id;

        public PiezaIdWrapper() {}

        public PiezaIdWrapper(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public CreateAutoparteRequest() {}

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public ModeloIdWrapper getModelo() {
        return modelo;
    }

    public void setModelo(ModeloIdWrapper modelo) {
        this.modelo = modelo;
    }

    public PiezaIdWrapper getPieza() {
        return pieza;
    }

    public void setPieza(PiezaIdWrapper pieza) {
        this.pieza = pieza;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
