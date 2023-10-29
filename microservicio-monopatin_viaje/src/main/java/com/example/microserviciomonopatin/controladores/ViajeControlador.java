package com.example.microserviciomonopatin.controladores;

import com.example.microserviciomonopatin.modelos.DTOS.CrearViajeDTO;
import com.example.microserviciomonopatin.modelos.entidades.Viaje;
import com.example.microserviciomonopatin.servicios.ViajeServicio;
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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }


    // FIX: Falta pasarle el id_cuenta, id_cliente e id_monopatin
    @PostMapping("")
    public ResponseEntity<?> crearViaje(@RequestBody CrearViajeDTO viaje){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.crearViaje(viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
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

    // FIX: Falta validar que existan id_cuenta, id_cliente e id_monopatin
    @PutMapping("/{id}")
    public ResponseEntity<?> editarViaje(@PathVariable Long id, @RequestBody Viaje viaje){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.editarViaje(id, viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }

    @GetMapping("/cantidadViajesMayorA/{cantidad}/año/{anio}")
    public ResponseEntity<?> cantidadViajesMayorAXAño(@PathVariable Integer cantidad, @PathVariable Integer anio){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.cantidadViajesMayorAXAño(cantidad, anio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}
