package com.example.microservicioviaje.controladores;

import com.example.microservicioviaje.entidades.Viaje;
import com.example.microservicioviaje.servicios.ViajeServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/viajes")
public class ViajeControlador {

    private final ViajeServicio viajeServicio;

    /**
     * Devuelve todos los viajes
     */
    @GetMapping("")
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.traerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }

    /**
     * Devuelve un viaje por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }

    /**
     * Crea a un viaje
     */
    @PostMapping("")
    public ResponseEntity<?> crearViaje(@RequestBody Viaje viaje){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.crearViaje(viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo crear el viaje.");
        }
    }

    /**
     * Elimina a un viaje
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.eliminarViaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Edita los datos de un viaje
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarViaje(@PathVariable Long id, @RequestBody Viaje viaje){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.editarViaje(id, viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }

}
