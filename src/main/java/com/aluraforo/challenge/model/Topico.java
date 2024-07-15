
package com.aluraforo.challenge.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String título;

    @NotBlank
    @Size(max = 500)
    private String mensaje;

    private LocalDateTime fechaDeCreación;

    @NotBlank
    private String status;

    @NotBlank
    private String autor;

    @NotBlank
    private String curso;

    @PrePersist
    protected void onCreate() {
        this.fechaDeCreación = LocalDateTime.now();
    }
}
