package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.modelos.entidades.Cuenta;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CuentaServicio {
    private final CuentaServicio cuentaRepositorio;

    @Transactional
    public Iterable<Cuenta> traerTodas() {
        return cuentaRepositorio.findAll();
    }

    @Transactional
    public Optional<Cuenta> traerPorId(Long id) throws Exception {
        Optional<Cuenta> cuentaRecuperada = cuentaRepositorio.findById(id);
        if (cuentaRecuperada.isPresent()) {
            return cuentaRecuperada;
        } else {
            throw new Exception("No se pudo encontrar la cuenta con el ID proporcionado.");
        }
    }

    @Transactional
    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepositorio.save(cuenta);
    }

    @Transactional
    public Optional<Cuenta> eliminarCuenta(Long id) throws Exception {
        Optional<Cuenta> cuentaEliminar = this.traerPorId(id);
        if(cuentaEliminar.isPresent()) {
            cuentaRepositorio.deleteById(id);
            return cuentaEliminar;
        }
        else {
            throw new Exception("Cuenta no encontrada");
        }
    }

    @Transactional
    public Cuenta editarCuenta(Long id, Cuenta usuario) throws Exception {
        try{
            Optional<Cuenta> cuentaEditar = this.traerPorId(id);
            if(cuentaEditar.isPresent()) {
                Cuenta cuentaEncontrada = cuentaEditar.get();
                cuentaEncontrada = cuentaRepositorio.save(usuario);
                return cuentaEncontrada;
            } else {
                throw new Exception("No se ha encontrado la cuenta que intenta editar.");
            }
        } catch (Exception e){
            throw new Exception("No se ha editado la cuenta.");
        }
    }
}
