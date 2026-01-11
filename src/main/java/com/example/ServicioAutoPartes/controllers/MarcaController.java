package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.dtos.MarcaDTO;
import com.example.ServicioAutoPartes.models.Marca;
import com.example.ServicioAutoPartes.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<MarcaDTO>> getAllMarcas() {
        try {
            List<Marca> marcas = marcaRepository.findAll();
            if (marcas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<MarcaDTO> marcaDTOs = marcas.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(marcaDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable("id") Long id) {
        Optional<Marca> marcaData = marcaRepository.findById(id);
        return marcaData.map(this::convertToDto)
                .map(marcaDTO -> new ResponseEntity<>(marcaDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private MarcaDTO convertToDto(Marca marca) {
        return new MarcaDTO(marca.getId(), marca.getNombre());
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
