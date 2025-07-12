package com.edwin.hernandez.foro_api.modelos;

import java.time.LocalDateTime;

import com.edwin.hernandez.foro_api.modelos.DTOs.ActualizarTopicoDTO;
import com.edwin.hernandez.foro_api.modelos.DTOs.RegistroTopicoDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column(unique = true)
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String estado;
    private String autor;
    private String curso;

    public Topico(RegistroTopicoDTO datos,LocalDateTime fecha) {
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = fecha;
        this.estado = "1";
        this.autor = datos.autor();
        this.curso = datos.curso();
    }

    public void actualizarTopico(@Valid ActualizarTopicoDTO datos) {
        if(datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if(datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if(datos.autor() != null){
            this.autor = datos.autor();
        }
        if(datos.curso() != null){
            this.curso = datos.curso();
        }
    }
}
