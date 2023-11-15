package com.example.microserviciousuario.controladores;

import com.example.microserviciousuario.modelos.entidades.Cuenta;
import com.example.microserviciousuario.security.AuthorityConstants;
import com.example.microserviciousuario.servicios.CuentaServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/cuentas")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    /**
     * Devuelve todas las cuentas
     */
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
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
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/{id_cuenta}/usuario/{id_usuario}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerCuentaUsuarioPorId(@PathVariable Long id_cuenta, @PathVariable Long id_usuario){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.traerCuentaUsuarioPorId(id_cuenta, id_usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Crea a una cuenta
     */
    @PostMapping("/{id_mercado_pago}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> crearCuenta(@PathVariable Long id_mercado_pago){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.crearCuenta(id_mercado_pago));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Elimina una cuenta
     */
    @DeleteMapping("{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
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
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> editarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaServicio.editarCuenta(id, cuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("{id_cuenta}/cargarSaldo/{monto}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> cargarSaldo(@PathVariable Long id_cuenta, @PathVariable Double monto){
        try{
            return ResponseEntity.status(200).body(cuentaServicio.cargarSaldo(id_cuenta, monto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("{id_cuenta}/restarSaldo/{monto}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> restarSaldo(@PathVariable Long id_cuenta, @PathVariable Double monto){
        try{
            return ResponseEntity.status(200).body(cuentaServicio.restarSaldo(id_cuenta, monto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
}
