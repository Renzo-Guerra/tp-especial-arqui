package com.example.microserviciocuenta.repositorios;

import com.example.microserviciocuenta.modelos.entidades.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepositorio extends CrudRepository<Cuenta, Long> {
}
