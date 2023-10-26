package com.example.microserviciousuario.repositorios;

import com.example.microserviciousuario.modelos.entidades.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepositorio extends CrudRepository<Cuenta, Long> {
}
