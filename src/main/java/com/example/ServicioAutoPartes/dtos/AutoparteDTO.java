package com.example.ServicioAutoPartes.dtos;

public class AutoparteDTO {
    private Long id;
    private String codigoProducto;
    private Double precio;
    private Integer stock;
    private String estado;
    private ModeloDTO modelo;
    private PiezaDTO pieza;

    public AutoparteDTO() {
    }

    public AutoparteDTO(Long id, String codigoProducto, Double precio, Integer stock, String estado, ModeloDTO modelo, PiezaDTO pieza) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.modelo = modelo;
        this.pieza = pieza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
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

    public ModeloDTO getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDTO modelo) {
        this.modelo = modelo;
    }

    public PiezaDTO getPieza() {
        return pieza;
    }

    public void setPieza(PiezaDTO pieza) {
        this.pieza = pieza;
    }
}
