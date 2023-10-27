package com.example.microserviciomonopatin.servicios;

import com.example.microserviciomonopatin.modelos.entidades.Viaje;
import com.example.microserviciomonopatin.repositorios.ViajeRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ViajeServicio {
    private final ViajeRepositorio viajeRepositorio;

    @Transactional
    public Iterable<Viaje> traerTodos() {
        return viajeRepositorio.findAll();
    }

    @Transactional
    public Optional<Viaje> traerPorId(Long id) {
        return viajeRepositorio.findById(id);
    }

    @Transactional
    public Viaje crearViaje(Viaje viaje) {
        return viajeRepositorio.save(viaje);
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
