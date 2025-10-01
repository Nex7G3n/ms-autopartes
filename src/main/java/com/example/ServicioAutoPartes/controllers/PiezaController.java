package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.models.Pieza;
import com.example.ServicioAutoPartes.repositories.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/piezas")
public class PiezaController {

    @Autowired
    private PiezaRepository piezaRepository;

    @PostMapping
    public ResponseEntity<Pieza> createPieza(@RequestBody Pieza pieza) {
        try {
            Pieza nuevaPieza = piezaRepository.save(pieza);
            return new ResponseEntity<>(nuevaPieza, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pieza>> getAllPiezas() {
        try {
            List<Pieza> piezas = piezaRepository.findAll();
            if (piezas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(piezas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pieza> getPiezaById(@PathVariable("id") Long id) {
        Optional<Pieza> piezaData = piezaRepository.findById(id);
        return piezaData.map(pieza -> new ResponseEntity<>(pieza, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pieza> updatePieza(@PathVariable("id") Long id, @RequestBody Pieza pieza) {
        Optional<Pieza> piezaData = piezaRepository.findById(id);

        if (piezaData.isPresent()) {
            Pieza _pieza = piezaData.get();
            _pieza.setNombre(pieza.getNombre());
            _pieza.setCategoria(pieza.getCategoria());
            _pieza.setDescripcion(pieza.getDescripcion());
            return new ResponseEntity<>(piezaRepository.save(_pieza), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePieza(@PathVariable("id") Long id) {
        try {
            piezaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
