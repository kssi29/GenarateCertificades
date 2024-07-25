package com.pvae.app.servicies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.stereotype.Service;

import com.pvae.app.models.UnidadModel;
import com.pvae.app.repositories.UnidadRepository;

import jakarta.transaction.Transactional;

@Service
public class UnidadService {
    
    private final UnidadRepository unidadRepository;

    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    public List<UnidadModel> listarUnidades() {
        return (List<UnidadModel>) unidadRepository.findAll();
    }

    public UnidadModel buscarUnidad(Long id) {
        return unidadRepository.findById(id).orElse(null);
    }

    @Transactional
    public void guardarUnidad(UnidadModel unidad) {
        unidadRepository.save(unidad);
    }

    @Transactional
    public void eliminarUnidad(Long idunidad) {
        UnidadModel unidad = unidadRepository.findById(idunidad).orElse(null);
        if (unidad != null && "Universidad Mayor de San Andres".equals(unidad.getNombre())) {
            throw new IllegalArgumentException("Universidad Mayor de San Andrés.");
        }
        unidadRepository.deleteById(idunidad);
    }

    public List<UnidadModel> obtenerUnidadesPadres(UnidadModel unidad) {
        List<UnidadModel> unidadesPadres = new ArrayList<>();
        UnidadModel unidadActual = unidad;

        while (unidadActual != null) {
            unidadesPadres.add(unidadActual);
            unidadActual = unidadActual.getUnidadPadre();
        }

        Collections.reverse(unidadesPadres);

        return unidadesPadres;
    }

}
