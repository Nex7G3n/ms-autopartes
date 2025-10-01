package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.models.Marca;
import com.example.ServicioAutoPartes.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca) {
        try {
            Marca nuevaMarca = marcaRepository.save(marca);
            return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Marca>> getAllMarcas() {
        try {
            List<Marca> marcas = marcaRepository.findAll();
            if (marcas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable("id") Long id) {
        Optional<Marca> marcaData = marcaRepository.findById(id);
        return marcaData.map(marca -> new ResponseEntity<>(marca, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable("id") Long id, @RequestBody Marca marca) {
        Optional<Marca> marcaData = marcaRepository.findById(id);

        if (marcaData.isPresent()) {
            Marca _marca = marcaData.get();
            _marca.setNombre(marca.getNombre());
            _marca.setPaisOrigen(marca.getPaisOrigen());
            return new ResponseEntity<>(marcaRepository.save(_marca), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMarca(@PathVariable("id") Long id) {
        try {
            marcaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
