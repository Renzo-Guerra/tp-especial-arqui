package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.modelos.DTOS.TransferenciaCuentaDTO;
import com.example.microserviciousuario.modelos.entidades.Cuenta;
import com.example.microserviciousuario.repositorios.CuentaRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CuentaServicio {
    private final CuentaRepositorio cuentaRepositorio;

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
    public Cuenta crearCuenta(Long id_cuenta_mercado_pago) {
        return cuentaRepositorio.save(new Cuenta(id_cuenta_mercado_pago));
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

    @Transactional
    public TransferenciaCuentaDTO cargarSaldo(Long id_cuenta, Double monto) throws Exception {
        if(monto.isNaN()){ throw new Exception("El monto ingresado no es un numero!") ;}
        if(monto <= 0){ throw new Exception("El monto ingresado debe ser mayor que 0!") ;}

        Optional<Cuenta> posible_cuenta = this.traerPorId(id_cuenta);

        if(posible_cuenta.isEmpty()){ throw new Exception("No existe cuenta con el id '" + id_cuenta + "'!"); }

        Cuenta cuenta = posible_cuenta.get();
        Double montoAnterior = cuenta.getSaldo();

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        Cuenta cuentaCambiada = this.cuentaRepositorio.save(cuenta);

        return new TransferenciaCuentaDTO(montoAnterior, cuentaCambiada.getSaldo(), monto);
    }


    @Transactional
    public TransferenciaCuentaDTO restarSaldo(Long id_cuenta, Double monto_quitar) throws Exception {
        if(monto_quitar.isNaN()){ throw new Exception("El monto a restar no es un numero!") ;}
        if(monto_quitar <= 0){ throw new Exception("El monto a restar debe ser mayor que 0!") ;}

        Optional<Cuenta> posible_cuenta = this.traerPorId(id_cuenta);

        if(posible_cuenta.isEmpty()){ throw new Exception("No existe cuenta con el id '" + id_cuenta + "'!"); }

        Cuenta cuenta = posible_cuenta.get();
        Double montoAnterior = cuenta.getSaldo();

        cuenta.setSaldo(cuenta.getSaldo() - monto_quitar);
        Cuenta cuentaCambiada = this.cuentaRepositorio.save(cuenta);

        return new TransferenciaCuentaDTO(montoAnterior, cuentaCambiada.getSaldo(), monto_quitar);
    }
}
