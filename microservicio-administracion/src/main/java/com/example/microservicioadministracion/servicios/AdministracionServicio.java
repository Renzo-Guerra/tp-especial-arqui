package com.example.microservicioadministracion.servicios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
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
        String url = "http://localhost:8002/monopatines";
        ResponseEntity<List<Monopatin>> respuesta = monopatinClienteRest.exchange(
                url,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<List<Monopatin>>() {
        });
        headers.setContentType(MediaType.APPLICATION_JSON);
        return respuesta.getBody();
    }
}
