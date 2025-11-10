package com.example.ServicioAutoPartes.controllers;

import com.example.ServicioAutoPartes.dtos.AutoparteDTO;
import com.example.ServicioAutoPartes.dtos.MarcaDTO;
import com.example.ServicioAutoPartes.dtos.ModeloDTO;
import com.example.ServicioAutoPartes.dtos.PiezaDTO;
import com.example.ServicioAutoPartes.models.Autoparte;
import com.example.ServicioAutoPartes.models.Marca;
import com.example.ServicioAutoPartes.models.Modelo;
import com.example.ServicioAutoPartes.models.Pieza;
import com.example.ServicioAutoPartes.repositories.AutoparteRepository;
import com.example.ServicioAutoPartes.repositories.ModeloRepository;
import com.example.ServicioAutoPartes.repositories.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autopartes")
public class AutoparteController {

    @Autowired
    private AutoparteRepository autoparteRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private PiezaRepository piezaRepository;

    @PostMapping
    public ResponseEntity<Autoparte> createAutoparte(@RequestBody Autoparte autoparte) {
        try {
            // Asume que el JSON de entrada contiene objetos "modelo" y "pieza" con solo sus "id"
            return modeloRepository.findById(autoparte.getModelo().getId()).flatMap(modelo ->
                    piezaRepository.findById(autoparte.getPieza().getId()).map(pieza -> {
                        autoparte.setModelo(modelo);
                        autoparte.setPieza(pieza);
                        Autoparte nuevaAutoparte = autoparteRepository.save(autoparte);
                        return new ResponseEntity<>(nuevaAutoparte, HttpStatus.CREATED);
                    })
            ).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutoparteDTO>> getAllAutopartes() {
        try {
            List<Autoparte> autopartes = autoparteRepository.findAll();
            if (autopartes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AutoparteDTO> autoparteDTOs = autopartes.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(autoparteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private AutoparteDTO convertToDto(Autoparte autoparte) {
        ModeloDTO modeloDTO = null;
        if (autoparte.getModelo() != null) {
            Modelo modelo = autoparte.getModelo();
            MarcaDTO marcaDTO = null;
            if (modelo.getMarca() != null) {
                Marca marca = modelo.getMarca();
                marcaDTO = new MarcaDTO(marca.getId(), marca.getNombre());
            }
            modeloDTO = new ModeloDTO(modelo.getId(), modelo.getNombre(), modelo.getAnio(), marcaDTO);
        }

        PiezaDTO piezaDTO = null;
        if (autoparte.getPieza() != null) {
            Pieza pieza = autoparte.getPieza();
            piezaDTO = new PiezaDTO(pieza.getId(), pieza.getNombre());
        }

        return new AutoparteDTO(
                autoparte.getId(),
                autoparte.getCodigoProducto(),
                autoparte.getPrecio(),
                autoparte.getStock(),
                autoparte.getEstado(),
                modeloDTO,
                piezaDTO
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoparteDTO> getAutoparteById(@PathVariable("id") Long id) {
        Optional<Autoparte> autoparteData = autoparteRepository.findById(id);
        return autoparteData.map(this::convertToDto)
                .map(autoparteDTO -> new ResponseEntity<>(autoparteDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<AutoparteDTO> getAutoparteByCodigo(@PathVariable("codigo") String codigo) {
        Optional<Autoparte> autoparteData = autoparteRepository.findByCodigoProducto(codigo);
        return autoparteData.map(this::convertToDto)
                .map(autoparteDTO -> new ResponseEntity<>(autoparteDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/modelo/{modeloId}")
    public ResponseEntity<List<AutoparteDTO>> getAutopartesByModelo(@PathVariable("modeloId") Long modeloId) {
        try {
            List<Autoparte> autopartes = autoparteRepository.findByModeloId(modeloId);
            if (autopartes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AutoparteDTO> autoparteDTOs = autopartes.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(autoparteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pieza/{piezaId}")
    public ResponseEntity<List<AutoparteDTO>> getAutopartesByPieza(@PathVariable("piezaId") Long piezaId) {
        try {
            List<Autoparte> autopartes = autoparteRepository.findByPiezaId(piezaId);
            if (autopartes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<AutoparteDTO> autoparteDTOs = autopartes.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(autoparteDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autoparte> updateAutoparte(@PathVariable("id") Long id, @RequestBody Autoparte autoparte) {
        Optional<Autoparte> autoparteData = autoparteRepository.findById(id);

        if (autoparteData.isPresent()) {
            return modeloRepository.findById(autoparte.getModelo().getId()).flatMap(modelo ->
                    piezaRepository.findById(autoparte.getPieza().getId()).map(pieza -> {
                        Autoparte _autoparte = autoparteData.get();
                        _autoparte.setCodigoProducto(autoparte.getCodigoProducto());
                        _autoparte.setPrecio(autoparte.getPrecio());
                        _autoparte.setStock(autoparte.getStock());
                        _autoparte.setEstado(autoparte.getEstado());
                        _autoparte.setModelo(modelo);
                        _autoparte.setPieza(pieza);
                        return new ResponseEntity<>(autoparteRepository.save(_autoparte), HttpStatus.OK);
                    })
            ).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Autoparte> updateStock(@PathVariable("id") Long id, @RequestParam("stock") int stock) {
        Optional<Autoparte> autoparteData = autoparteRepository.findById(id);

        if (autoparteData.isPresent()) {
            Autoparte _autoparte = autoparteData.get();
            _autoparte.setStock(stock);
            return new ResponseEntity<>(autoparteRepository.save(_autoparte), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAutoparte(@PathVariable("id") Long id) {
        try {
            autoparteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
