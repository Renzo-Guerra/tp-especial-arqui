package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.DTOS.CrearViajeDTO;
import com.example.microserviciomonopatin.modelos.DTOS.ReporteMonopatinesXViaje;
import com.example.microserviciomonopatin.modelos.DTOS.ReporteFacturacionDTO;
import com.example.microserviciomonopatin.modelos.DTOS.UsuarioCuentaDTO;
import com.example.microserviciomonopatin.modelos.entidades.*;
import com.example.microserviciomonopatin.repositorios.MonopatinRepositorio;
import com.example.microserviciomonopatin.repositorios.ViajeRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ViajeServicio {
    private final ViajeRepositorio viajeRepositorio;
    private final MonopatinServicio monopatinServicio;
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
        Monopatin monopatin = this.monopatinServicio.traerPorId(viaje.getId_monopatin());

        // Error, estado monopatin no disponible
        if(!monopatin.getEstado().equals("disponible")){throw new Exception("El monopatin no se encuentra disponible!!!");}

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


        // Validamos que la cuenta esté vinculada con el usuario
        if(!cuenta.contieneUsuario(viaje.getId_usuario())){
            throw new Exception("La cuenta " + cuenta.getId() + " no está relacionada con el usuario " + viaje.getId_usuario() + "!!!");
        }

        // Validamos que la cuenta tenga credito
        if(cuenta.getSaldo() <= 0){ throw new Exception("La cuenta no dispone de fondos suficientes!!!"); }

        // Traemos el valor de la tarifa
        HttpEntity<Void> reqEntity2 = new HttpEntity<>(headers);
        ResponseEntity<Optional<Tarifa>> response2 = restTemplate.exchange(
                "http://localhost:8001/administracion/tarifas/ultima",
                HttpMethod.GET,
                reqEntity2,
                new ParameterizedTypeReference<>() {}
        );
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(response2.getBody().isEmpty()) {
            throw new Exception("No existe una tarifa predefinida.");
        }
        Tarifa tarifa = response2.getBody().get();

        // Persistimos el cambio de estado del monopatin
        monopatin.setEstado("ocupado");

        try{
            this.monopatinServicio.crear(monopatin);
        }catch(Exception e){
            // En caso de que el estado no sea valido volvemos a setear el estado del monopatin como valido
            monopatin.setEstado("disponible");
            throw e;
        }

        // Persistimos el nuevo viaje y lo devolvemos
        return viajeRepositorio.save(new Viaje(viaje.getId_cuenta(), viaje.getId_usuario(), viaje.getId_monopatin(), tarifa.getTarifa(), tarifa.getPorc_recargo()));
    }

//    @Transactional
//    public Optional<Viaje> eliminarViaje(Long id) throws Exception {
//        Optional<Viaje> viajeEliminar = this.traerPorId(id);
//
//        if(viajeEliminar.isPresent()) {
//            viajeRepositorio.deleteById(id);
//            return viajeEliminar;
//        }
//        else {
//            throw new Exception("Usuario no encontrado");
//        }
//    }

//    @Transactional
//    public Object editarViaje(Long id, Viaje viaje) throws Exception {
//        try{
//            Optional<Viaje> viajeEditar = this.traerPorId(id);
//            if(viajeEditar.isPresent()) {
//                Viaje viajeEncontrado = viajeEditar.get();
//                viajeEncontrado = viajeRepositorio.save(viaje);
//                return viajeEncontrado;
//            } else {
//                throw new Exception("No se ha encontrado el viaje que intenta editar.");
//            }
//        } catch (Exception e){
//            throw new Exception("No se ha editar el viaje.");
//        }
//    }

    @Transactional
    public List<ReporteMonopatinesXViaje> cantidadViajesMayorAXAño(Integer cantidad, Integer anio) throws Exception {
        return viajeRepositorio.cantidadViajesMayorAXAño(cantidad, anio);
    }

    @Transactional
    public Viaje finalizarViaje(Long id_viaje) throws Exception {
        // Validamos que exista el viaje
        Optional<Viaje> posible_viaje_a_finalizar = this.traerPorId(id_viaje);
        if(posible_viaje_a_finalizar.isEmpty()){ throw new Exception("No existe el viaje con id " + id_viaje + "!!!"); }

        Viaje viaje_a_finalizar = posible_viaje_a_finalizar.get();

        // Nos traemos la cuenta por id
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Optional<Cuenta>> response = restTemplate.exchange(
                "http://localhost:8004/cuentas/" + viaje_a_finalizar.getId_cuenta(),
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        Cuenta cuenta = response.getBody().get();

        // Nos traemos el monopatin por id
        Monopatin monopatin = this.monopatinServicio.traerPorId(viaje_a_finalizar.getId_monopatin());

        // Validamos que el monopatin esté en una locacion valida para dejarse (una parada disponible)
        HttpEntity<Void> httpEntity2 = new HttpEntity<>(headers);
        ResponseEntity<Parada> response2 = restTemplate.exchange(
                "http://localhost:8003/paradas/buscarParada/latitud/" + monopatin.getLatitud() + "/longitud/" + monopatin.getLongitud(),
                HttpMethod.GET,
                httpEntity2,
                new ParameterizedTypeReference<>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        Parada parada = response2.getBody();
        if(!parada.getIsHabilitada()){ throw new Exception("La parada no está disponible!"); }

        monopatin.setEstado("disponible");
        viaje_a_finalizar.setFin(LocalDateTime.now());
        viaje_a_finalizar.setSegundos_estacionado((long) (Math.floor(Math.random() + 1) * Math.floor(Math.random() + 1) * 100));
        // Los kilometros recorridos los llenamos de forma random,
        // pero la idea seria que se calcule en base al tiempo del viaje
        viaje_a_finalizar.setKm_recorridos(Math.floor((Math.random() + 0.1D)  * 10));
        // EL costo del viaje es en funcion de los kilometros recorridos

        LocalDateTime fechaInicio = viaje_a_finalizar.getInicio();
        LocalDateTime fechaFin = viaje_a_finalizar.getFin();

        // Calcula la diferencia en minutos
        Duration duracion = Duration.between(fechaInicio, fechaFin);
        long minutosViaje = duracion.toMinutes();

        Double costo_total = minutosViaje * viaje_a_finalizar.getTarifa();
        if(viaje_a_finalizar.getSegundos_estacionado() > (15*60)){
            costo_total *= viaje_a_finalizar.getPorc_recargo();
        }

        viaje_a_finalizar.setCosto_total_viaje(costo_total);

        // Descontamos el costo del viaje a la cuenta asociada al viaje
        HttpEntity<Void> httpEntity3 = new HttpEntity<>(headers);
        ResponseEntity<Parada> response3 = restTemplate.exchange(
                "http://localhost:8004/cuentas/" + cuenta.getId() + "/restarSaldo/" + costo_total,
                HttpMethod.PUT,
                httpEntity3,
                new ParameterizedTypeReference<>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cambiamos el estado del monopatin a "disponible"
        HttpEntity<Monopatin> httpEntity4 = new HttpEntity<>(monopatin, headers);
        ResponseEntity<Monopatin> response4 = restTemplate.exchange(
                "http://localhost:8002/monopatines/" + monopatin.getId_monopatin(),
                HttpMethod.PUT,
                httpEntity4,
                new ParameterizedTypeReference<>() {});
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Damos por finalizado el viaje y devolvemos el viaje con todas sus columnas
        return viajeRepositorio.save(viaje_a_finalizar);
    }

    @Transactional
    public ReporteFacturacionDTO facturacionViajesRangoMesesPorAnio(Integer mes1, Integer mes2, Integer anio) {
        return viajeRepositorio.facturacionViajesRangoMesesPorAnio(mes1, mes2, anio);
    }
}
