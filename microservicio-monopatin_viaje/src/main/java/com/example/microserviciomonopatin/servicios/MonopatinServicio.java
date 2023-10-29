package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.modelos.DTOS.MonopatinKilometrajeDTO;
import com.example.microserviciomonopatin.repositorios.MonopatinRepositorio;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class MonopatinServicio {
    private final MonopatinRepositorio monopatinRepositorio;

    @Transactional
    public Iterable<Monopatin> traerTodos() {
        return monopatinRepositorio.findAll();
    }

    // FIX: Falta endpoint que traiga los monopatines que tienen el estado "disponible"

    @Transactional
    public Monopatin traerPorId(Long id_monopatin) throws Exception {
        Optional<Monopatin> monopatin = monopatinRepositorio.findById(id_monopatin);

        if(monopatin.isEmpty()){
            throw new Exception("No existe un monopatin con id '" + id_monopatin + "'!");
        }

        return monopatin.get();
    }

    // FIX: Falta validar que el idgps no esté asignado a otro monopatin
    @Transactional
    public Monopatin crear(Monopatin monopatin) throws Exception {
        this.validarEstado(monopatin.getEstado());
        return monopatinRepositorio.save(monopatin);
    }

    // FIX: En realidad habria que cambiar el estado del monopatin a "deshabilitado"
    @Transactional
    public Monopatin eliminar(Long id_monopatin) throws Exception {
        Monopatin monopatin_eliminar = this.traerPorId(id_monopatin);

        monopatinRepositorio.deleteById(id_monopatin);
        return monopatin_eliminar;
    }


    // FIX: Falta validar que en caso de que se cambie el idgps, este no esté asignado a otro monopatin
    @Transactional
    public Monopatin editar(Long idMonopatin, Monopatin nuevaInfo) throws Exception {
        System.out.println("La nueva es: " + nuevaInfo);
        this.validarEstado(nuevaInfo.getEstado());
        Monopatin monopatin_editar = this.traerPorId(idMonopatin);

        monopatin_editar.setGps(nuevaInfo.getGps());
        monopatin_editar.setLatitud(nuevaInfo.getLatitud());
        monopatin_editar.setLongitud(nuevaInfo.getLongitud());
        monopatin_editar.setEstado(nuevaInfo.getEstado());

        return monopatinRepositorio.save(monopatin_editar);
    }


    // Valida que el estado del monopatin sea valido
    private void validarEstado(String estado) throws Exception {
        String[] estados_monopatin_validos = {"disponible", "ocupado", "mantenimiento", "deshabilitado"};

        if(!Arrays.asList(estados_monopatin_validos).contains(estado)){
            throw new Exception("El estado '" + estado + "' es invalido! Estados validos: " + Arrays.toString(estados_monopatin_validos));
        }
    }

    public List<MonopatinKilometrajeDTO> traerOrdenadosPorKilometros(String ord) {
        if(ord.equals("ASC"))
            return this.monopatinRepositorio.traerOrdenadosPorKilometrosASC();
        else
            return this.monopatinRepositorio.traerOrdenadosPorKilometrosDESC();
    }
}
