package com.example.microserviciousuario.repositorios;

import com.example.microserviciousuario.modelos.DTOS.UsuarioCuentaDTO;
import com.example.microserviciousuario.modelos.entidades.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepositorio extends CrudRepository<Cuenta, Long> {
    /**
     * @param id de la cuenta a recuperar
     * @return Optional<Cuenta> la cual tiene asociada una lista de Usuarios
     */
    @Query("SELECT c From Cuenta c JOIN FETCH c.usuarios WHERE c.id = :id")
    Optional<Cuenta> findByIdWithCuentas(Long id);
}
