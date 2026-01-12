package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.dtos.MarcaDTO;
import com.example.ServicioAutoPartes.dtos.ModeloDTO;
import com.example.ServicioAutoPartes.dtos.CreateModeloRequest;
import com.example.ServicioAutoPartes.models.Marca;
import com.example.ServicioAutoPartes.models.Modelo;
import com.example.ServicioAutoPartes.repositories.ModeloRepository;
import com.example.ServicioAutoPartes.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public ResponseEntity<ModeloDTO> createModelo(@RequestBody CreateModeloRequest request) {
        try {
            // Debug: imprimir qué datos llegan
            System.out.println("=== DEBUG CREATE MODELO ===");
            
            // Imprimir el JSON recibido usando ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            System.out.println("JSON recibido: " + jsonRequest);
            
            System.out.println("Nombre: " + request.getNombre());
            System.out.println("Anio: " + request.getAnio());
            System.out.println("Marca: " + request.getMarca());
            if (request.getMarca() != null) {
                System.out.println("Marca ID: " + request.getMarca().getId());
            }
            System.out.println("==========================");
            
            // Validar que se proporcionen los datos requeridos
            if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
                System.out.println("ERROR: Nombre es nulo o vacío");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Validar que se proporciona una marca
            if (request.getMarca() == null || request.getMarca().getId() == null) {
                System.out.println("ERROR: Marca es nula o no tiene ID");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Buscar la marca por ID
            Optional<Marca> marcaOpt = marcaRepository.findById(request.getMarca().getId());
            if (marcaOpt.isEmpty()) {
                System.out.println("ERROR: Marca con ID " + request.getMarca().getId() + " no encontrada");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Crear el modelo con la marca encontrada
            Modelo nuevoModelo = new Modelo();
            nuevoModelo.setNombre(request.getNombre());
            nuevoModelo.setAnio(request.getAnio());
            nuevoModelo.setMarca(marcaOpt.get());
            
            Modelo modeloGuardado = modeloRepository.save(nuevoModelo);
            ModeloDTO modeloDTO = convertToDto(modeloGuardado);
            System.out.println("Modelo creado con éxito: " + modeloGuardado.getId());
            return new ResponseEntity<>(modeloDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR INTERNO: " + e.getMessage());
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
    public ResponseEntity<ModeloDTO> updateModelo(@PathVariable("id") Long id, @RequestBody CreateModeloRequest request) {
        try {
            Optional<Modelo> modeloData = modeloRepository.findById(id);
            
            if (modeloData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Validar que se proporcionen los datos requeridos
            if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
                System.out.println("ERROR: Nombre es nulo o vacío");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Validar que se proporciona una marca
            if (request.getMarca() == null || request.getMarca().getId() == null) {
                System.out.println("ERROR: Marca es nula o no tiene ID");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Buscar la marca por ID
            Optional<Marca> marcaOpt = marcaRepository.findById(request.getMarca().getId());
            if (marcaOpt.isEmpty()) {
                System.out.println("ERROR: Marca con ID " + request.getMarca().getId() + " no encontrada");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Actualizar el modelo
            Modelo _modelo = modeloData.get();
            _modelo.setNombre(request.getNombre());
            _modelo.setAnio(request.getAnio());
            _modelo.setMarca(marcaOpt.get());
            
            Modelo updatedModelo = modeloRepository.save(_modelo);
            ModeloDTO modeloDTO = convertToDto(updatedModelo);
            System.out.println("Modelo actualizado con éxito: " + updatedModelo.getId());
            return new ResponseEntity<>(modeloDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR INTERNO en updateModelo: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
