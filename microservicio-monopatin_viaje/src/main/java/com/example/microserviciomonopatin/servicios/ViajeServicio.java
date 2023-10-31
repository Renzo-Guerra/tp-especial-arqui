package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.DTOS.CrearViajeDTO;
import com.example.microserviciomonopatin.modelos.entidades.*;
import com.example.microserviciomonopatin.repositorios.MonopatinRepositorio;
import com.example.microserviciomonopatin.repositorios.ViajeRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ViajeServicio {
    private final ViajeRepositorio viajeRepositorio;
    private final MonopatinRepositorio monopatinRepositorio;
    private final RestTemplate restTemplate;

    @Transactional
    public Iterable<Viaje> traerTodos() {
        return viajeRepositorio.findAll();
    }

    @Transactional
    public Optional<Viaje> traerPorId(Long id) {
        return viajeRepositorio.findById(id);
    }

    @Transactional
    public Viaje crearViaje(CrearViajeDTO viaje) throws Exception {
        Optional<Monopatin> monopatin = this.monopatinRepositorio.findById(viaje.getId_monopatin());

        // Error monopatin inexistente
        if(monopatin.isEmpty()){throw new Exception("No existe monopatin con el id: " + viaje.getId_monopatin());}
        // Error, estado monopatin no disponible
        if(!monopatin.get().getEstado().equals("disponible")){throw new Exception("El monopatin no se encuentra disponible!!!");}

        // Traemos la cuenta:
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<Cuenta> response = restTemplate.exchange(
                "http://localhost:8004/cuentas/" + viaje.getId_cuenta(),
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        Cuenta cuenta = response.getBody();

        // Validamos que la cuenta no esté deshabilitada
        if(!cuenta.getIsHabilitada()){throw new Exception("La cuenta con el id '" + cuenta.getId() + "' está deshabilitada!!!"); }

        // Validamos que la cuenta tenga credito
        if(cuenta.getSaldo() <= 0){ throw new Exception("La cuenta no dispone de fondos suficientes!!!"); }

        // Validamos que el Usuario exista:
        HttpEntity<Void> reqEntity2 = new HttpEntity<>(headers);
        ResponseEntity<Optional<Usuario>> response2 = restTemplate.exchange(
                "http://localhost:8004/usuarios/" + viaje.getId_usuario(),
                HttpMethod.GET,
                reqEntity2,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Traemos el valor de la tarifa
        HttpEntity<Void> reqEntity3 = new HttpEntity<>(headers);
        ResponseEntity<Optional<Tarifa>> response3 = restTemplate.exchange(
                "http://localhost:8001/administracion/tarifas/ultima",
                HttpMethod.GET,
                reqEntity3,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(response3.getBody().isEmpty()) {
            throw new Exception("No existe una tarifa predefinida.");
        }
        Tarifa tarifa = response3.getBody().get();

        return viajeRepositorio.save(new Viaje(viaje.getId_cuenta(), viaje.getId_usuario(), viaje.getId_monopatin(), tarifa.getTarifa(), tarifa.getPorc_recargo()));
    }

    @Transactional
    public Optional<Viaje> eliminarViaje(Long id) throws Exception {
        Optional<Viaje> viajeEliminar = this.traerPorId(id);

        if(viajeEliminar.isPresent()) {
            viajeRepositorio.deleteById(id);
            return viajeEliminar;
        }
        else {
            throw new Exception("Usuario no encontrado");
        }
    }

    @Transactional
    public Object editarViaje(Long id, Viaje viaje) throws Exception {
        try{
            Optional<Viaje> viajeEditar = this.traerPorId(id);
            if(viajeEditar.isPresent()) {
                Viaje viajeEncontrado = viajeEditar.get();
                viajeEncontrado = viajeRepositorio.save(viaje);
                return viajeEncontrado;
            } else {
                throw new Exception("No se ha encontrado el viaje que intenta editar.");
            }
        } catch (Exception e){
            throw new Exception("No se ha editar el viaje.");
        }
    }

    @Transactional
    public List<Viaje> cantidadViajesMayorAXAño(Integer cantidad, Integer anio) throws Exception {
        return viajeRepositorio.cantidadViajesMayorAXAño(cantidad, anio);
    }

}
