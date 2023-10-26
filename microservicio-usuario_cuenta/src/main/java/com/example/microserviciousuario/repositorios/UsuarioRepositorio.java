package com.example.microserviciousuario.repositorios;

import com.example.microserviciousuario.modelos.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long> {


}
