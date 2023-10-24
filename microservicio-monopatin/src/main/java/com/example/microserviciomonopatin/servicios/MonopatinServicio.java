package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.repositorios.MonopatinRespositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Monopatin traerPorId(Long id_monopatin) {
        Optional<Monopatin> monopatin = monopatinRespositorio.findById(id_monopatin);
        return monopatin.orElse(null);

    }

    @Transactional
    public Monopatin crear(Monopatin monopatin) {
        return monopatinRespositorio.save(monopatin);
    }

    @Transactional
    public Monopatin eliminar(Long id_monopatin) throws Exception {
        Monopatin monopatin_eliminar = this.traerPorId(id_monopatin);

        if(monopatin_eliminar != null){
            monopatinRespositorio.deleteById(id_monopatin);
            return monopatin_eliminar;
        }else{
            throw new Exception("Usuario no encontrado!");
        }
    }

    @Transactional
    public Monopatin editar(Long idMonopatin, Monopatin nuevaInfo) throws Exception {
        Monopatin monopatin_editar = this.traerPorId(idMonopatin);

        if(monopatin_editar != null){
            monopatin_editar.setGps(nuevaInfo.getGps());
            monopatin_editar.setLatitud(nuevaInfo.getLatitud());
            monopatin_editar.setLongitud(nuevaInfo.getLongitud());

            return monopatinRespositorio.save(monopatin_editar);
        }else{
            throw new Exception("Usuario no encontrado!");
        }
    }
}
