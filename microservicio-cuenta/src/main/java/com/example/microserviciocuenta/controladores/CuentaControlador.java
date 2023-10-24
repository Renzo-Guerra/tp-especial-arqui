package com.example.microserviciocuenta.controladores;

import com.example.microserviciocuenta.modelos.entidades.Cuenta;
import com.example.microserviciocuenta.servicios.CuentaServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/cuentas")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    /**
     * Devuelve todas las cuentas
     */
    @GetMapping("")
    public ResponseEntity<?> traerTodas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.traerTodas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos.");
        }
    }

    /**
     * Devuelve una cuenta por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }

    /**
     * Crea a una cuenta
     */
    @PostMapping("")
    public ResponseEntity<?> crearCuenta(@RequestBody Cuenta cuenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.crearCuenta(cuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo crear la cuenta.");
        }
    }

    /**
     * Elimina una cuenta
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.eliminarCuenta(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Edita los datos de una cuenta
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.editarCuenta(id, cuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudo recuperar el dato.");
        }
    }
}
