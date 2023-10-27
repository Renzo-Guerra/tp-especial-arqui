package com.example.microservicioadministracion.controladores;

import com.example.microservicioadministracion.modelos.entidades.Monopatin;
import com.example.microservicioadministracion.modelos.entidades.Parada;
import com.example.microservicioadministracion.servicios.AdministracionServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("/monopatines/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @PathVariable String estado){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.editarEstadoMonopatin(id, estado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/monopatines")
    public ResponseEntity<?> agregarMonopatin(@RequestBody Monopatin monopatin){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.agregarMonopatin(monopatin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.eliminarMonopatin(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/paradas")
    public ResponseEntity<?> agregarParada(@RequestBody Parada parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.agregarParada(parada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("/paradas/{id}/cambiarDisponibilidad")
    public ResponseEntity<?> cambiarDisponibilidad(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.cambiarDisponibilidad(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/paradas")
    public ResponseEntity<?> traerTodasParadas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerTodasParadas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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
