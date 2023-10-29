package com.example.microservicioadministracion.servicios;

import com.example.microservicioadministracion.modelos.DTOS.MonopatinKilometrajeDTO;
import com.example.microservicioadministracion.modelos.DTOS.TarifaCrearTarifaDTO;
import com.example.microservicioadministracion.modelos.entidades.*;
import com.example.microservicioadministracion.repositorios.TarifaRespositorio;
import com.example.microserviciomonopatin.modelos.entidades.Viaje;
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

    public Cuenta cambiarEstadoCuenta(Long id_cuenta, String habilitada) throws Exception {
        habilitada = habilitada.toLowerCase();
        if(!habilitada.equals("true") && !habilitada.equals("false")){
            throw new Exception("Estado de cuenta invalido. Parametros validos: ('true' | 'false') !");
        }

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<Cuenta> respuesta = restTemplate.exchange(
                "http://localhost:8004/cuentas/" + id_cuenta,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {
                });
        headers.setContentType(MediaType.APPLICATION_JSON);

        Cuenta cuenta_editar = respuesta.getBody();

        // Si el estado de la cuenta es el mismo por el cual se quiere cambiar, directamente corta aca
        if(cuenta_editar.getIsHabilitada().equals(Boolean.getBoolean(habilitada))){
            return cuenta_editar;
        }
        // En caso de que fuese distinto, hay que setear el nuevo valor y guardar el cambio en el microservicio
        cuenta_editar.setIsHabilitada(Boolean.getBoolean(habilitada));

        HttpEntity<Cuenta> reqEntity2 = new HttpEntity<>(cuenta_editar, headers);
        ResponseEntity<Cuenta> respuesta2 = restTemplate.exchange(
                "http://localhost:8004/cuentas/" + id_cuenta,
                HttpMethod.PUT,
                reqEntity2,
                new ParameterizedTypeReference<>() {
                });
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta2.getBody();
    }

    public List<Viaje> reporteCantidadViajesPorAnio(Integer cantidad, Integer anio) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Viaje>> respuesta = restTemplate.exchange(
                "http://localhost:8002/viajes/cantidadViajesMayorA/" + cantidad + "/año/ "+ anio,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<List<Viaje>>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        return respuesta.getBody();
    }

    public List<MonopatinKilometrajeDTO> reporteMonopatinesOrderByKilometros(String orden) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MonopatinKilometrajeDTO>> response = restTemplate.exchange(
                "http://localhost:8002/monopatines/kilometros?orden=" + orden,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        return response.getBody();
    }
}
