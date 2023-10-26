package com.example.microservicioadministracion.servicios;

import com.example.microservicioadministracion.modelos.entidades.Monopatin;
import com.example.microservicioadministracion.modelos.entidades.MonopatinCambiarEstadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdministracionServicio {
    @Autowired
    private RestTemplate monopatinClienteRest;

    public List<Monopatin> traerTodosMonopatin(){
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
        MonopatinCambiarEstadoDTO monopatinDto = new MonopatinCambiarEstadoDTO(id_monopatin, estado);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MonopatinCambiarEstadoDTO> reqEntity = new HttpEntity<>(monopatinDto, headers);
        ResponseEntity<Monopatin> respuesta = monopatinClienteRest.exchange(
                "http://localhost:8002/monopatines/estado",
                HttpMethod.PUT,
                reqEntity,
                new ParameterizedTypeReference<>() {
                });
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }
}
