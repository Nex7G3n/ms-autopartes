package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.dtos.MarcaDTO;
import com.example.ServicioAutoPartes.dtos.ModeloDTO;
import com.example.ServicioAutoPartes.models.Marca;
import com.example.ServicioAutoPartes.models.Modelo;
import com.example.ServicioAutoPartes.repositories.ModeloRepository;
import com.example.ServicioAutoPartes.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autopartes/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping
    public ResponseEntity<ModeloDTO> createModelo(@RequestBody Modelo modelo) {
        try {
            // Validar que se proporcionen los datos requeridos
            if (modelo.getNombre() == null || modelo.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Validar que se proporciona una marca
            if (modelo.getMarca() == null || modelo.getMarca().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Buscar la marca por ID
            Optional<Marca> marcaOpt = marcaRepository.findById(modelo.getMarca().getId());
            if (marcaOpt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Asignar la marca encontrada
            modelo.setMarca(marcaOpt.get());
            
            Modelo nuevoModelo = modeloRepository.save(modelo);
            ModeloDTO modeloDTO = convertToDto(nuevoModelo);
            return new ResponseEntity<>(modeloDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ModeloDTO>> getAllModelos() {
        try {
            List<Modelo> modelos = modeloRepository.findAll();
            if (modelos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ModeloDTO> modeloDTOs = modelos.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(modeloDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloDTO> getModeloById(@PathVariable("id") Long id) {
        Optional<Modelo> modeloData = modeloRepository.findById(id);
        return modeloData.map(this::convertToDto)
                .map(modeloDTO -> new ResponseEntity<>(modeloDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ModeloDTO>> getModelosByMarca(@PathVariable("marcaId") Long marcaId) {
        try {
            List<Modelo> modelos = modeloRepository.findByMarcaId(marcaId);
            if (modelos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ModeloDTO> modeloDTOs = modelos.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(modeloDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ModeloDTO convertToDto(Modelo modelo) {
        MarcaDTO marcaDTO = null;
        if (modelo.getMarca() != null) {
            Marca marca = modelo.getMarca();
            marcaDTO = new MarcaDTO(marca.getId(), marca.getNombre());
        }
        return new ModeloDTO(modelo.getId(), modelo.getNombre(), modelo.getAnio(), marcaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloDTO> updateModelo(@PathVariable("id") Long id, @RequestBody Modelo modelo) {
        Optional<Modelo> modeloData = modeloRepository.findById(id);

        if (modeloData.isPresent()) {
            return marcaRepository.findById(modelo.getMarca().getId()).map(marca -> {
                Modelo _modelo = modeloData.get();
                _modelo.setNombre(modelo.getNombre());
                _modelo.setAnio(modelo.getAnio());
                _modelo.setMarca(marca);
                Modelo updatedModelo = modeloRepository.save(_modelo);
                return new ResponseEntity<>(convertToDto(updatedModelo), HttpStatus.OK);
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
