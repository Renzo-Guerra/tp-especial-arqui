package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.modelos.DTOS.UsuarioCreacionDTO;
import com.example.microserviciousuario.modelos.entidades.Usuario;
import com.example.microserviciousuario.modelos.entidades.Viaje;
import com.example.microserviciousuario.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
@AllArgsConstructor
public class UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final RestTemplate restTemplate;

    @Transactional
    public Iterable<Usuario> traerTodos() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public Optional<Usuario> traerPorId(Long id) throws Exception {
        Optional<Usuario> usuarioRecuperado = usuarioRepositorio.findById(id);
        if (usuarioRecuperado.isPresent()) {
            return usuarioRecuperado;
        } else {
            throw new Exception("No se pudo encontrar el usuario con el ID proporcionado.");
        }
    }

    @Transactional
    public Usuario crearUsuario(UsuarioCreacionDTO usuario) {
        Usuario nuevoUsuario = new Usuario(usuario.getNombre(), usuario.getApellido());
        return usuarioRepositorio.save(nuevoUsuario);
    }

    @Transactional
    public Optional<Usuario> eliminarUsuario(Long id) throws Exception {
        Optional<Usuario> usuarioEliminar = this.traerPorId(id);

        if(usuarioEliminar.isPresent()) {
            usuarioRepositorio.deleteById(id);
            return usuarioEliminar;
        }
        else {
            throw new Exception("Usuario no encontrado");
        }
    }

    @Transactional
    public Usuario editarUsuario(Long id, Usuario usuario) throws Exception {
        try{
            Optional<Usuario> usuarioEditar = this.traerPorId(id);
            if(usuarioEditar.isPresent()) {
                Usuario usuarioEncontrado = usuarioEditar.get();
                usuarioEncontrado = usuarioRepositorio.save(usuario);
                return usuarioEncontrado;
            } else {
                throw new Exception("No se ha encontrado el usuario que intenta editar.");
            }
        } catch (Exception e){
            throw new Exception("No se ha editado el usuario.");
        }
    }

    /*@Transactional
    public Viaje finalizarViaje(Integer id_viaje) throws Exception {
        // Traemos el viaje:
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);
        ResponseEntity<Viaje> response = restTemplate.exchange(
                "http://localhost:8002/viajes/" + id_viaje,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        Viaje viaje = response.getBody();

        if(viaje.getFin() == null) {
            viaje.setFin(LocalDateTime.now());
            Random random = new Random();
            Double randomDouble = random.nextDouble() * 100; // seteamos de forma aleatoria los kms recorridos
            viaje.setKm_recorridos(randomDouble);
            Long randomLong = random.nextLong() * 100; // seteamos de forma aleatoria el tiempo estacionado
            viaje.setSegundos_estacionado(randomLong);

            Double importe = 0.0;
            Double tarifa = viaje.getTarifa();
            Double porc_rec = viaje.getPorc_recargo();
            Long seg_estacionado = viaje.getSegundos_estacionado();
            Double quinceMinSegundos = new Double(15 * 60);
            Long tiempo_viaje = Duration.between(viaje.getFin(), viaje.getInicio()).toMinutes();
            importe = (tiempo_viaje - ((seg_estacionado * 60) - quinceMinSegundos) * tarifa)
            if(seg_estacionado > quinceMinSegundos) {
                importe += ((seg_estacionado - quinceMinSegundos) * tarifa * porc_rec);
            }
            viaje.setCosto_viaje(importe);
        } else {
            throw new Exception("El viaje que intenta finalizar, ya fue finalizado.");
        }

        // Traemos el valor de la tarifa
       /* HttpEntity<Void> reqEntity3 = new HttpEntity<>(headers);
        ResponseEntity<Optional<Tarifa>> response3 = restTemplate.exchange(
                "http://localhost:8001/administracion/tarifas/ultima",
                HttpMethod.GET,
                reqEntity3,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Habilitar monopatin como dispo
        // Descontar dinero de cuenta:


    }
    */
}
