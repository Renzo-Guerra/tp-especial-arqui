package com.example.microserviciousuario.controladores;

import com.example.microserviciousuario.modelos.dto.UsuarioCreacion;
import com.example.microserviciousuario.modelos.entidades.Usuario;
import com.example.microserviciousuario.servicios.UsuarioServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    /**
     * Devuelve todos los usuarios
     */
    @GetMapping("")
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.traerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }

    /**
     * Devuelve un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }

    /**
     * Crea a un usuario
     */
    @PostMapping("")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreacion usuario){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.crearUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo crear el usuario.");
        }
    }

    /**
     * Elimina a un usuario
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.eliminarUsuario(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Edita los datos de un usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.editarUsuario(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }


}
