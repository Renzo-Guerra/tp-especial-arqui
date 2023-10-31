package com.example.microserviciousuario.repositorios;

import com.example.microserviciousuario.modelos.entidades.Cuenta;
import com.example.microserviciousuario.modelos.entidades.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long> {
    /**
     * @param id del usuairo a recuperar
     * @return Optional<Usuario> la cual tiene asociada una lista de Cuentas
     */
    @Query("SELECT u From Usuario u JOIN FETCH u.cuentas WHERE u.id = :id")
    Optional<Cuenta> findByIdWithUsuarios(Long id);
}
