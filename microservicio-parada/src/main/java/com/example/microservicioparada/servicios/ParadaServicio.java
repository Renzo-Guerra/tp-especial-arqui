package com.example.microservicioparada.servicios;

import com.example.microservicioparada.modelos.entidades.Parada;
import com.example.microservicioparada.repositorios.ParadaRespositorio;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
public class ParadaServicio {
    private final ParadaRespositorio paradaRespositorio;

    @Transactional
    public Iterable<Parada> traerTodos() {
        return paradaRespositorio.findAll();
    }

    @Transactional
    public Parada traerPorId(Long id_parada) throws Exception {
        Optional<Parada> parada = paradaRespositorio.findById(id_parada);

        if(parada.isEmpty()){
            throw new Exception("No existe una parada con id '" + id_parada + "'!");
        }

        return parada.get();
    }

    @Transactional
    public Parada crear(Parada parada) throws Exception {
        // Validamos que la nueva parada no tenga las mismas coordenadas que alguna ya existente
        this.middlewareCoordenadaExiste(parada.getLatitud(), parada.getLongitud());

        return paradaRespositorio.save(parada);
    }

//    @Transactional
//    public Parada eliminar(Long id_parada) throws Exception {
//        Parada parada_eliminar = this.traerPorId(id_parada);
//
//        this.paradaRespositorio.deleteById(id_parada);
//        return parada_eliminar;
//    }

    @Transactional
    public Parada editar(Long id_parada, Parada nuevaInfo) throws Exception {
        Parada parada_editar = this.traerPorId(id_parada);

        if(!Objects.equals(nuevaInfo.getLatitud(), parada_editar.getLatitud()) || !Objects.equals(nuevaInfo.getLongitud(), parada_editar.getLongitud())) {
            this.middlewareCoordenadaExiste(nuevaInfo.getLatitud(), nuevaInfo.getLongitud());
        }

        parada_editar.setLatitud(nuevaInfo.getLatitud());
        parada_editar.setLongitud(nuevaInfo.getLongitud());
        parada_editar.setIsHabilitada(nuevaInfo.getIsHabilitada());

        return paradaRespositorio.save(parada_editar);
    }


    // MIDDLEWARE Verifica que no exista una parada con la misma latitud y longitud
    private void middlewareCoordenadaExiste(Double latitud, Double longitud) throws Exception {
        Optional<Parada> parada = this.paradaRespositorio.traerPorCoordenadas(latitud, longitud);

        if(parada.isPresent()){
            throw new Exception("Ya existe la parada '" + parada.get().getId_parada() + "' con esa latitud y longitud!");
        }
    }

    public Parada buscarParadaPorCoordenadas(Double latitud, Double longitud) throws Exception {
        if(latitud == null || longitud == null){ throw new Exception("Latitud o longitud son nulas!!!"); }
        Optional<Parada> posible_parada = this.paradaRespositorio.traerPorCoordenadas(latitud, longitud);

        if(posible_parada.isEmpty()){
            throw new Exception("No existe parada con esas coordenadas!");
        }

        return posible_parada.get();
    }
}
