package com.aluraforo.challenge.model.repository;

import com.aluraforo.challenge.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTítuloAndMensaje(String título, String mensaje);
}