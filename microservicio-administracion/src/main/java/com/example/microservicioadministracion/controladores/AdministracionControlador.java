package com.example.microservicioadministracion.controladores;

import com.example.microservicioadministracion.modelos.entidades.Rol;
import com.example.microservicioadministracion.servicios.AdministracionServicio;
import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.modelos.entidades.MonopatinCambiarEstadoDTO;
import com.example.microserviciomonopatin.servicios.MonopatinServicio;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Data
@RequestMapping("/administracion")
public class AdministracionControlador {
    //private final RolServicio rolServicio;
    private final AdministracionServicio administracionServicio;

    /**
     * Lista todos los monopatin
     */
    @GetMapping("/monopatines")
    public ResponseEntity<?> traerTodosMonopatin(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerTodosMonopatin());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }

    @PutMapping("/monopatines/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @PathVariable String estado){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.editarEstadoMonopatin(id, estado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }

    /**
     * Devuelve todos los roles
     */
    //@GetMapping("/roles")
    /*public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rolServicio.traerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }*/

    /**
     * Devuelve un rol por ID
     */
    //@GetMapping("/roles/{id}")
    /*public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rolServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }*/

    /**
     * Crea a un rol
     */
    //@PostMapping("/roles")
    /*public ResponseEntity<?> crearRol(@RequestBody Rol rol){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rolServicio.crearRol(rol));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo crear el rol.");
        }
    }*/

    /**
     * Elimina un rol
     */
    //@DeleteMapping("/roles/{id}")
    /*public ResponseEntity<?> eliminarRol(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rolServicio.eliminarRol(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }*/

    /**
     * Edita los datos de un rol
     */
    //@PutMapping("/roles/{id}")
    /*public ResponseEntity<?> editarRol(@PathVariable Long id, @RequestBody Rol rol){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rolServicio.editarRol(id, rol));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }*/
}
