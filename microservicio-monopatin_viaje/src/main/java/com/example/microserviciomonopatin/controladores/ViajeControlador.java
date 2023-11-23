package com.example.microserviciomonopatin.controladores;

import com.example.microserviciomonopatin.modelos.DTOS.CrearViajeDTO;
import com.example.microserviciomonopatin.modelos.entidades.Viaje;
import com.example.microserviciomonopatin.security.AuthorityConstants;
import com.example.microserviciomonopatin.servicios.ViajeServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.traerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Devuelve un viaje por ID
     */
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> crearViaje(@RequestBody CrearViajeDTO viaje, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.crearViaje(viaje, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id_viaje}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> finalizarViaje(@PathVariable Long id_viaje, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.finalizarViaje(id_viaje, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<?> eliminarViaje(@PathVariable Long id){
//        try{
//            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.eliminarViaje(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    // IMPORTANTE: Deberíamos eliminar el metodo editar viaje y el metodo eliminar viaje, no tienen sentido en la aplicacion
//    @PutMapping("/{id}")
//    public ResponseEntity<?> editarViaje(@PathVariable Long id, @RequestBody Viaje viaje){
//        try{
//            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.editarViaje(id, viaje));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo recuperar el dato.");
//        }
//    }

    @GetMapping("/cantidadViajesMayorA/{cantidad}/año/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> cantidadViajesMayorAXAño(@PathVariable Integer cantidad, @PathVariable Integer anio){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.cantidadViajesMayorAXAño(cantidad, anio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/facturacionViajesDesde/{mes1}/hasta/{mes2}/año/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> facturacionViajesRangoMesesPorAnio(@PathVariable Integer mes1, @PathVariable Integer mes2, @PathVariable Integer anio){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeServicio.facturacionViajesRangoMesesPorAnio(mes1, mes2, anio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
