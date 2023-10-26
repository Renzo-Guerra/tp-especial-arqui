package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.modelos.entidades.MonopatinCambiarEstadoDTO;
import com.example.microserviciomonopatin.repositorios.MonopatinRespositorio;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Data
public class MonopatinServicio {
    private final MonopatinRespositorio monopatinRespositorio;

    @Transactional
    public Iterable<Monopatin> traerTodos() {
        return monopatinRespositorio.findAll();
    }

    @Transactional
    public Monopatin traerPorId(Long id_monopatin) throws Exception {
        Optional<Monopatin> monopatin = monopatinRespositorio.findById(id_monopatin);

        if(monopatin.isEmpty()){
            throw new Exception("No existe un monopatin con id '" + id_monopatin + "'!");
        }

        return monopatin.get();
    }

    @Transactional
    public Monopatin crear(Monopatin monopatin) throws Exception {
        this.validarEstado(monopatin.getEstado());
        return monopatinRespositorio.save(monopatin);
    }

    @Transactional
    public Monopatin eliminar(Long id_monopatin) throws Exception {
        Monopatin monopatin_eliminar = this.traerPorId(id_monopatin);

        monopatinRespositorio.deleteById(id_monopatin);
        return monopatin_eliminar;
    }

    @Transactional
    public Monopatin editar(Long idMonopatin, Monopatin nuevaInfo) throws Exception {
        this.validarEstado(nuevaInfo.getEstado());
        Monopatin monopatin_editar = this.traerPorId(idMonopatin);

        monopatin_editar.setGps(nuevaInfo.getGps());
        monopatin_editar.setLatitud(nuevaInfo.getLatitud());
        monopatin_editar.setLongitud(nuevaInfo.getLongitud());

        return monopatinRespositorio.save(monopatin_editar);
    }


    // Valida que el estado del monopatin sea valido
    private void validarEstado(String estado) throws Exception {
        String[] estados_monopatin_validos = {"disponible", "ocupado", "mantenimiento", "deshabilitado"};

        if(!Arrays.asList(estados_monopatin_validos).contains(estado)){
            throw new Exception("El estado '" + estado + "' es invalido! Estados validos: " + Arrays.toString(estados_monopatin_validos));
        }
    }
}
