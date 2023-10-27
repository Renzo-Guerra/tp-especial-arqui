package com.example.microservicioadministracion.servicios;

import com.example.microservicioadministracion.modelos.entidades.Monopatin;
import com.example.microservicioadministracion.modelos.entidades.Parada;
import com.example.microservicioadministracion.modelos.entidades.Tarifa;
import com.example.microservicioadministracion.modelos.entidades.TarifaCrearTarifaDTO;
import com.example.microservicioadministracion.repositorios.TarifaRespositorio;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class AdministracionServicio {

    private final TarifaRespositorio tarifaRespositorio;

    @Autowired
    private RestTemplate monopatinClienteRest;
    @Autowired
    private RestTemplate restTemplate;

    public List<Monopatin> traerTodosMonopatin() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Monopatin>> respuesta = monopatinClienteRest.exchange(
                "http://localhost:8002/monopatines",
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<List<Monopatin>>() {
        });
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }

    public Monopatin editarEstadoMonopatin(Long id_monopatin, String estado) throws Exception {
        // Traemos el monopatin por id
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<Monopatin> respuesta = monopatinClienteRest.exchange(
                "http://localhost:8002/monopatines/" + id_monopatin,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {});

        // Editamos el monopatin
        Monopatin monopatin = respuesta.getBody();
        monopatin.setEstado(estado);

        // Lo guardamos ya modificado
        HttpEntity<Monopatin> reqEntity2 = new HttpEntity<>(monopatin, headers);
        ResponseEntity<Monopatin> respuesta2 = monopatinClienteRest.exchange(
                "http://localhost:8002/monopatines/" + id_monopatin,
                HttpMethod.PUT,
                reqEntity2,
                new ParameterizedTypeReference<>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta2.getBody();
    }

    public Monopatin agregarMonopatin(Monopatin monopatin) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Monopatin> reqEntity = new HttpEntity<>(monopatin, headers);
        ResponseEntity<Monopatin> respuesta = monopatinClienteRest.exchange(
                "http://localhost:8002/monopatines",
                HttpMethod.POST,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }

    public Monopatin eliminarMonopatin(Long id_monopatin) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Long> reqEntity = new HttpEntity<>(id_monopatin, headers);
        ResponseEntity<Monopatin> respuesta = monopatinClienteRest.exchange(
           "http://localhost:8002/monopatines/" + id_monopatin,
                HttpMethod.DELETE,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }

    public Parada agregarParada(Parada parada) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Parada> reqEntity = new HttpEntity<>(parada, headers);
        ResponseEntity<Parada> respuesta = restTemplate.exchange(
                "http://localhost:8003/paradas",
                HttpMethod.POST,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }

    public Parada cambiarDisponibilidad(Long id) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<Parada> respuesta = restTemplate.exchange(
                "http://localhost:8003/paradas/" + id,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        Parada paradaEditarEstado = respuesta.getBody();
        paradaEditarEstado.setIsHabilitada(!paradaEditarEstado.getIsHabilitada());

        HttpEntity<Parada> reqEntity2 = new HttpEntity<>(paradaEditarEstado, headers);
        ResponseEntity<Parada> respuesta2 = restTemplate.exchange(
                "http://localhost:8003/paradas/" + id,
                HttpMethod.PUT,
                reqEntity2,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta2.getBody();

    }

    public List<Parada> traerTodasParadas() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Parada>> respuesta = restTemplate.exchange(
            "http://localhost:8003/paradas",
            HttpMethod.GET,
            reqEntity,
            new ParameterizedTypeReference<List<Parada>>() {
            });
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }


    public Iterable<Tarifa> traerTodasTarifas() throws Exception {
        return tarifaRespositorio.findAll();
    }

    /**
     * Creamos una tarifa. Se comprueba si la ultima tarifa creada no tiene fecha de caducacion (== null), si es asi, se setea la fecha de caducacion y se crea la nueva tarifa.
     */
    public Tarifa crearTarifa(TarifaCrearTarifaDTO tarifa) throws Exception {
        Optional<Tarifa> ultimaTarifa = this.traerUltimaTarifaCreada();
        if(ultimaTarifa.isPresent()) {
            if(ultimaTarifa.get().getFecha_caducacion() == null) {
                ultimaTarifa.get().setFecha_caducacion(LocalDateTime.now());
                tarifaRespositorio.save(ultimaTarifa.get());
            }
        }
        Tarifa t = new Tarifa(tarifa.getTarifa(), tarifa.getPorc_recargo());
        return tarifaRespositorio.save(t);
    }

    public Optional<Tarifa> traerUltimaTarifaCreada() throws Exception {
        Optional<Tarifa> t = tarifaRespositorio.buscarUltimaTarifa();
        return t;
    }
}
