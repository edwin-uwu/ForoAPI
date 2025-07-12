package com.edwin.hernandez.foro_api.controlador;

import com.edwin.hernandez.foro_api.modelos.DTOs.ActualizarTopicoDTO;
import com.edwin.hernandez.foro_api.modelos.DTOs.ListaTopicos;
import com.edwin.hernandez.foro_api.modelos.DTOs.RegistroTopicoDTO;
import com.edwin.hernandez.foro_api.negocio.TopicosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.data.domain.Pageable;
import java.util.stream.Stream;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicosService topicosService;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid RegistroTopicoDTO datos, UriComponentsBuilder uriComponentsBuilder){
        var topicoRegistrado = topicosService.registrarTopico(datos);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRegistrado.id()).toUri();

        return ResponseEntity.created(uri).body(topicoRegistrado);
        //return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ListaTopicos>> listar(@PageableDefault(size = 10) Pageable paginacion ,@RequestParam(required = false) String curso,@RequestParam(required = false) Integer anio){

        var datos = topicosService.listarTopicos(paginacion,curso,anio);
        return  ResponseEntity.ok(datos);
    }

    @GetMapping("/{id}")
    public ResponseEntity topicoId(@PathVariable Long id){
        var topicoEncontrado = topicosService.topicoId(id);

        return ResponseEntity.ok(topicoEncontrado);
    }
    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid ActualizarTopicoDTO datos){

        var topico = topicosService.actualizar(datos);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){

        topicosService.eliminarTopico(id);

        return ResponseEntity.noContent().build();
    }
}
