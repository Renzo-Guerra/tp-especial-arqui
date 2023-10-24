package com.example.microservicioadministracion.repositorios;

import com.example.microservicioadministracion.modelos.entidades.Rol;
import org.springframework.data.repository.CrudRepository;

public interface RolRepositorio extends CrudRepository<Rol, Long> {
}
