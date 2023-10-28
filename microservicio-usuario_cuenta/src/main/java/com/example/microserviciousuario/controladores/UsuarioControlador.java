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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }


    /*@PostMapping("/viajes/usuario/{id_usuario}/cuenta/{id_cuenta}/monopatin/{id_monopatin}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Integer id_usuario, @PathVariable Integer id_cuenta, @PathVariable Integer id_monopatin) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.finalizarViaje(id_usuario, id_cuenta, id_monopatin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }*/
    @PostMapping("/viajes/{id_viaje}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Integer id_viaje) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.finalizarViaje(id_viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }


}
