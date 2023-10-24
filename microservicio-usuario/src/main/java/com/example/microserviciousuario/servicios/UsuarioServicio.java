package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.modelos.entidades.Usuario;
import com.example.microserviciousuario.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Iterable<Usuario> traerTodos() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public Optional<Usuario> traerPorId(Long id) throws Exception {
        Optional<Usuario> usuarioRecuperado = usuarioRepositorio.findById(id);
        if (usuarioRecuperado.isPresent()) {
            return usuarioRecuperado;
        } else {
            throw new Exception("No se pudo encontrar el usuario con el ID proporcionado.");
        }
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Optional<Usuario> eliminarUsuario(Long id) throws Exception {
        Optional<Usuario> usuarioEliminar = this.traerPorId(id);

        if(usuarioEliminar.isPresent()) {
            usuarioRepositorio.deleteById(id);
            return usuarioEliminar;
        }
        else {
            throw new Exception("Usuario no encontrado");
        }
    }

    @Transactional
    public Usuario editarUsuario(Long id, Usuario usuario) throws Exception {
        try{
            Optional<Usuario> usuarioEditar = this.traerPorId(id);
            if(usuarioEditar.isPresent()) {
                Usuario usuarioEncontrado = usuarioEditar.get();
                usuarioEncontrado = usuarioRepositorio.save(usuario);
                return usuarioEncontrado;
            } else {
                throw new Exception("No se ha encontrado el usuario que intenta editar.");
            }
        } catch (Exception e){
            throw new Exception("No se ha editado el usuario.");
        }
    }


}
