package com.aluraforo.challenge.controller;

import com.aluraforo.challenge.model.Topico;
import com.aluraforo.challenge.model.repository.ITopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {
    @Autowired
    private ITopicoRepository tópicoRepository;

    @PostMapping
    public ResponseEntity<?> crearTópico(@Valid @RequestBody Topico topico) {
        Optional<Topico> existente = tópicoRepository.findByTítuloAndMensaje(topico.getTítulo(), topico.getMensaje());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("Tópico duplicado");
        }
        Topico nuevoTópico = tópicoRepository.save(topico);
        return ResponseEntity.ok(nuevoTópico);
    }

    @GetMapping
    public List<Topico> listarTópicos() {
        return tópicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTópico(@PathVariable Long id) {
        Optional<Topico> tópico = tópicoRepository.findById(id);
        if (!tópico.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tópico.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTópico(@PathVariable Long id, @Valid @RequestBody Topico tópicoDetalles) {
        Optional<Topico> tópicoExistente = tópicoRepository.findById(id);
        if (!tópicoExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Topico tópico = tópicoExistente.get();
        tópico.setTítulo(tópicoDetalles.getTítulo());
        tópico.setMensaje(tópicoDetalles.getMensaje());
        tópico.setStatus(tópicoDetalles.getStatus());
        tópico.setAutor(tópicoDetalles.getAutor());
        tópico.setCurso(tópicoDetalles.getCurso());
        Topico actualizado = tópicoRepository.save(tópico);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTópico(@PathVariable Long id) {
        Optional<Topico> tópico = tópicoRepository.findById(id);
        if (!tópico.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tópicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}