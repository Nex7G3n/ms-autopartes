package com.example.ServicioAutoPartes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "autopartes")
public class Autoparte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_producto", unique = true, nullable = false, length = 50)
    private String codigoProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_id", nullable = false)
    @JsonIgnoreProperties("autopartes")
    private Modelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieza_id", nullable = false)
    @JsonIgnoreProperties("autopartes")
    private Pieza pieza;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "estado", length = 20)
    private String estado;

    public Autoparte() {
    }

    public Autoparte(String codigoProducto, Modelo modelo, Pieza pieza, Double precio, Integer stock, String estado) {
        this.codigoProducto = codigoProducto;
        this.modelo = modelo;
        this.pieza = pieza;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
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

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
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
