package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.dtos.PiezaDTO;
import com.example.ServicioAutoPartes.models.Pieza;
import com.example.ServicioAutoPartes.repositories.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<PiezaDTO>> getAllPiezas() {
        try {
            List<Pieza> piezas = piezaRepository.findAll();
            if (piezas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<PiezaDTO> piezaDTOs = piezas.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(piezaDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PiezaDTO> getPiezaById(@PathVariable("id") Long id) {
        Optional<Pieza> piezaData = piezaRepository.findById(id);
        return piezaData.map(this::convertToDto)
                .map(piezaDTO -> new ResponseEntity<>(piezaDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private PiezaDTO convertToDto(Pieza pieza) {
        return new PiezaDTO(pieza.getId(), pieza.getNombre());
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
