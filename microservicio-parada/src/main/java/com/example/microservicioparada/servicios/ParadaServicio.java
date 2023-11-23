package com.example.microservicioparada.servicios;

import com.example.microservicioparada.modelos.DTOs.request.ReqParadaDTO;
import com.example.microservicioparada.modelos.DTOs.response.ResParadaDTO;
import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import com.example.microservicioparada.repositorios.ParadaMongoRepositorio;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
public class ParadaServicio {
    private final ParadaMongoRepositorio paradaMongoRespositorio;

    @Transactional(readOnly = true)
    public List<ResParadaDTO> traerTodos() {
        return paradaMongoRespositorio.findAll().stream().map(p -> new ResParadaDTO(p)).toList();
    }

   // @Transactional(readOnly = true)
   // public Iterable<ParadaMongo> traerTodosMongo() {
        //return paradaMongoRespositorio.findAll();
   // }


    @Transactional(readOnly = true)
    public ResParadaDTO traerPorId(String id_parada) throws Exception {
        Optional<ParadaMongo> parada = paradaMongoRespositorio.findById(id_parada);

        if(parada.isEmpty()){
            throw new Exception("No existe una parada con id '" + id_parada + "'!");
        }

        return new ResParadaDTO(parada.get());
    }

    @Transactional
    public ResParadaDTO crear(ReqParadaDTO parada) throws Exception {
        // Validamos que la nueva parada no tenga las mismas coordenadas que alguna ya existente
        this.middlewareCoordenadaExiste(parada.getLatitud(), parada.getLongitud());

        ParadaMongo paradaCreada = paradaMongoRespositorio.save(
                new ParadaMongo(parada.getLatitud(),
                        parada.getLongitud(),
                        parada.getIsHabilitada()));

        return new ResParadaDTO(paradaCreada);
    }

//    @Transactional
//    public Parada eliminar(Long id_parada) throws Exception {
//        Parada parada_eliminar = this.traerPorId(id_parada);
//
//        this.paradaRespositorio.deleteById(id_parada);
//        return parada_eliminar;
//    }

    @Transactional
    public ResParadaDTO editar(String id_parada, ReqParadaDTO nuevaInfo) throws Exception {
        Optional<ParadaMongo> posible_parada = this.paradaMongoRespositorio.findById(id_parada);

        if(posible_parada.isEmpty()){
            throw new Exception("No existe una parada con id '" + id_parada + "'!");
        }
        ParadaMongo parada_editar = posible_parada.get();

        if(!Objects.equals(nuevaInfo.getLatitud(), parada_editar.getLatitud()) || !Objects.equals(nuevaInfo.getLongitud(), parada_editar.getLongitud())) {
            this.middlewareCoordenadaExiste(nuevaInfo.getLatitud(), nuevaInfo.getLongitud());
        }

        parada_editar.setLatitud(nuevaInfo.getLatitud());
        parada_editar.setLongitud(nuevaInfo.getLongitud());
        parada_editar.setIsHabilitada(nuevaInfo.getIsHabilitada());

        return new ResParadaDTO(paradaMongoRespositorio.save(parada_editar));
    }


    // MIDDLEWARE Verifica que no exista una parada con la misma latitud y longitud
    private void middlewareCoordenadaExiste(Double latitud, Double longitud) throws Exception {
        List<ParadaMongo> parada = this.paradaMongoRespositorio.traerPorCoordenadas(latitud, longitud, PageRequest.of(0, 1));

        if(!parada.isEmpty()){
            throw new Exception("Ya existe la parada '" + parada.get(0).getId_parada() + "' con esa latitud y longitud!");
        }
    }

    public ResParadaDTO buscarParadaPorCoordenadas(Double latitud, Double longitud) throws Exception {
        if(latitud == null || longitud == null){ throw new Exception("Latitud o longitud son nulas!!!"); }
        List<ParadaMongo> posible_parada = this.paradaMongoRespositorio.traerPorCoordenadas(latitud, longitud, PageRequest.of(0, 1));

        if(posible_parada.isEmpty()){
            throw new Exception("No existe parada con esas coordenadas!");
        } else {
            return new ResParadaDTO(posible_parada.get(0));
        }


    }
}
