package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento;
import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.modelos.DTOS.MonopatinKilometrajeDTO;
import com.example.microserviciomonopatin.repositorios.MonopatinRepositorio;
import org.springframework.transaction.annotation.Transactional;
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

    public List<MonopatinKilometrajeDTO> traerOrdenadosPorKilometros(String orden) throws Exception {
        // En caso de que no se haya pasado parametro
        if(orden == null){
            return this.monopatinRepositorio.traerOrdenadosPorKilometrosDESC();
        }

        // Verificamos que el valor asociado a este haya sido uno valido
        orden = orden.toUpperCase();
        if(!orden.equals("ASC") && !orden.equals("DESC")){
            throw new Exception("El parametró opcional 'orden' solo puede tomar el valor 'asc' o 'desc'!");
        }

        // En caso que el valor asociado sea ASC
        if(orden.equals("ASC"))
            return this.monopatinRepositorio.traerOrdenadosPorKilometrosASC();

        // En caso que el valor asociado sea DESC
        return this.monopatinRepositorio.traerOrdenadosPorKilometrosDESC();
    }

    public List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoConPausas(String orden) throws Exception {
        // En caso de que no se haya pasado parametro
        if(orden == null){
            return this.monopatinRepositorio.traerOrdenadosPorTiempoConPausasDESC();
        }

        // Verificamos que el valor asociado a este haya sido uno valido
        orden = orden.toUpperCase();
        if(!orden.equals("ASC") && !orden.equals("DESC")){
            throw new Exception("El parametró opcional 'orden' solo puede tomar el valor 'asc' o 'desc'!");
        }

        // En caso que el valor asociado sea ASC
        if(orden.equals("ASC"))
            return this.monopatinRepositorio.traerOrdenadosPorTiempoConPausasASC();

        // En caso que el valor asociado sea DESC
        return this.monopatinRepositorio.traerOrdenadosPorTiempoConPausasDESC();
    }

    public List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoSinPausas(String orden) throws Exception {
        // En caso de que no se haya pasado parametro
        if(orden == null){
            return this.monopatinRepositorio.traerOrdenadosPorTiempoSinPausasDESC();
        }

        // Verificamos que el valor asociado a este haya sido uno valido
        orden = orden.toUpperCase();
        if(!orden.equals("ASC") && !orden.equals("DESC")){
            throw new Exception("El parametró opcional 'orden' solo puede tomar el valor 'asc' o 'desc'!");
        }

        // En caso que el valor asociado sea ASC
        if(orden.equals("ASC"))
            return this.monopatinRepositorio.traerOrdenadosPorTiempoSinPausasASC();

        // En caso que el valor asociado sea DESC
        return this.monopatinRepositorio.traerOrdenadosPorTiempoSinPausasDESC();
    }

    public Iterable<Monopatin> getMonopatinesCercanos(Double latitud, Double longitud, Double rango) throws Exception {
        if(latitud == null || longitud == null || rango == null){
            throw new Exception("Alguno de los parametros pasados es nulo, verificar latitud, longitud y rango!!!");
        }
        Double latitudMinima = latitud - rango;
        Double latitudMaxima = latitud + rango;
        Double longitudMinima = longitud - rango;
        Double longitudMaxima = longitud + rango;

        return this.monopatinRepositorio.getMonopatinesCercanos(latitudMinima, latitudMaxima, longitudMinima, longitudMaxima);
    }
}
