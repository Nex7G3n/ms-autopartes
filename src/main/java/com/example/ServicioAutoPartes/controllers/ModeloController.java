package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.models.Modelo;
import com.example.ServicioAutoPartes.repositories.ModeloRepository;
import com.example.ServicioAutoPartes.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping
    public ResponseEntity<Modelo> createModelo(@RequestBody Modelo modelo) {
        try {
            // Se asume que el JSON de entrada para Modelo contiene un objeto "marca" con solo el "id"
            return marcaRepository.findById(modelo.getMarca().getId()).map(marca -> {
                modelo.setMarca(marca);
                Modelo nuevoModelo = modeloRepository.save(modelo);
                return new ResponseEntity<>(nuevoModelo, HttpStatus.CREATED);
            }).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> getAllModelos() {
        try {
            List<Modelo> modelos = modeloRepository.findAll();
            if (modelos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(modelos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModeloById(@PathVariable("id") Long id) {
        Optional<Modelo> modeloData = modeloRepository.findById(id);
        return modeloData.map(modelo -> new ResponseEntity<>(modelo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Modelo>> getModelosByMarca(@PathVariable("marcaId") Long marcaId) {
        try {
            List<Modelo> modelos = modeloRepository.findByMarcaId(marcaId);
            if (modelos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(modelos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> updateModelo(@PathVariable("id") Long id, @RequestBody Modelo modelo) {
        Optional<Modelo> modeloData = modeloRepository.findById(id);

        if (modeloData.isPresent()) {
            return marcaRepository.findById(modelo.getMarca().getId()).map(marca -> {
                Modelo _modelo = modeloData.get();
                _modelo.setNombre(modelo.getNombre());
                _modelo.setAnio(modelo.getAnio());
                _modelo.setMarca(marca);
                return new ResponseEntity<>(modeloRepository.save(_modelo), HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteModelo(@PathVariable("id") Long id) {
        try {
            modeloRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
