package com.edwin.hernandez.foro_api.negocio;

import com.edwin.hernandez.foro_api.dominio.TopicoRepository;
import com.edwin.hernandez.foro_api.modelos.DTOs.ActualizarTopicoDTO;
import com.edwin.hernandez.foro_api.modelos.DTOs.ListaTopicos;
import com.edwin.hernandez.foro_api.modelos.DTOs.RegistroTopicoDTO;
import com.edwin.hernandez.foro_api.modelos.Topico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicosService {

    @Autowired
    private TopicoRepository topicoRepository;

    public ListaTopicos registrarTopico(RegistroTopicoDTO datos){
        var fechaCreacion = LocalDateTime.now();

        var topico = new Topico(datos,fechaCreacion);
        topicoRepository.save(topico);

        return new ListaTopicos(topico);
    }

    public ListaTopicos topicoId(Long id) {
        var topicoEncontrado = topicoRepository.getReferenceById(id);
        return new ListaTopicos(topicoEncontrado);
    }

    public Page<ListaTopicos> listarTopicos(Pageable paginacion,String curso,Integer anio) {
        Page<Topico> datos = null;
        if(curso != null && anio != null){
            return topicoRepository.buscarPorCursoYAnio(curso,anio,paginacion).map(ListaTopicos::new);
        }
        if(curso != null)
        {
            return topicoRepository.findByCurso(curso,paginacion).map(ListaTopicos::new);
        }
        if(anio != null){
            return topicoRepository.findByanio(anio,paginacion).map(ListaTopicos::new);
        }
        return topicoRepository.findAll(paginacion).map(ListaTopicos::new);
    }
    public ListaTopicos actualizar(ActualizarTopicoDTO datos){
        Topico topicoExiste = topicoRepository.getReferenceById(datos.id());
        topicoExiste.actualizarTopico(datos);

        return new ListaTopicos(topicoExiste);
    }
    public void eliminarTopico(Long id)
    {
        Topico topicoExiste = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado con ID: " + id));

        topicoRepository.deleteById(id);

    }
}
