package com.example.microservicioadministracion.controladores;

import com.example.microservicioadministracion.modelos.DTOS.request.ReqParadaDTO;
import com.example.microservicioadministracion.modelos.entidades.Monopatin;
import com.example.microservicioadministracion.modelos.entidades.Parada;
import com.example.microservicioadministracion.modelos.DTOS.TarifaCrearTarifaDTO;
import com.example.microservicioadministracion.servicios.AdministracionServicio;
import com.example.microserviciomonopatin.security.AuthorityConstants;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/administracion")
public class AdministracionControlador {
    //private final RolServicio rolServicio;
    private final AdministracionServicio administracionServicio;


    // Inicio segunda parte

    /**
     * Lista todos los monopatin
     */
    @GetMapping("/monopatines")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerTodosMonopatin(@RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerTodosMonopatin(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }


    @PutMapping("/monopatines/{id}/estado/{estado}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @PathVariable String estado, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.editarEstadoMonopatin(id, estado, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/monopatines")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> agregarMonopatin(@RequestBody Monopatin monopatin, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.agregarMonopatin(monopatin, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

//    @DeleteMapping("/monopatines/{id}")
//    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id){
//        try{
//            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.eliminarMonopatin(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
//        }
//    }

    @PostMapping("/paradas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> agregarParada(@RequestBody ReqParadaDTO parada, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.agregarParada(parada, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("/paradas/{id}/cambiarDisponibilidad")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> cambiarDisponibilidad(@PathVariable String id, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.cambiarDisponibilidad(id, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/paradas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerTodasParadas(@RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerTodasParadas(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Devuelve todas las tarifas
     */
    @GetMapping("/tarifas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerTodasTarifas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerTodasTarifas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/tarifas/ultima")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerUltimaTarifa(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.traerUltimaTarifaCreada());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Devuelve todas las tarifas
     */
    @PostMapping("/tarifas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> crearTarifa(@RequestBody TarifaCrearTarifaDTO tarifa){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.crearTarifa(tarifa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("/cuentas/{id_cuenta}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> cambiarEstadoCuenta(@PathVariable Long id_cuenta, @RequestParam(name = "habilitada") String habilitada, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.cambiarEstadoCuenta(id_cuenta, habilitada, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/reportes/cantidadViajesMayorA/{cantidad}/anio/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> reporteCantidadViajesPorAnio(@PathVariable Integer cantidad, @PathVariable Integer anio, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.reporteCantidadViajesPorAnio(cantidad, anio, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/reportes/facturacionViajesDesde/{mes1}/hasta/{mes2}/anio/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> reporteFacturacionViajesRangoMesesPorAnio(@PathVariable Integer mes1, @PathVariable Integer mes2, @PathVariable Integer anio, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.facturacionViajesDesdeHastaAnio(mes1, mes2, anio, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/reportes/monopatines/kilometros")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> reporteMonopatinesOrderByKilometros(@RequestParam(name = "orden", required = false) String orden, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.reporteMonopatinesOrderByKilometros(orden, token));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/reportes/monopatines/tiempos/conPausas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> reporteMonopatinesTiemposConPausas(@RequestParam(name = "orden", required = false) String orden, @RequestHeader(name = "Authorization") String token){
          try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.reporteMonopatinesTiemposConPausas(orden, token));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/reportes/monopatines/tiempos/sinPausas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> reporteMonopatinesTiemposSinPausas(@RequestParam(name = "orden", required = false) String orden, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(administracionServicio.reporteMonopatinesTiemposSinPausas(orden, token));
        }catch (Exception e){
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
