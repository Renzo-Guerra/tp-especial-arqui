package com.example.microserviciomonopatin.controladores;

import com.example.microserviciomonopatin.security.AuthorityConstants;
import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.servicios.MonopatinServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/monopatines")
public class MonopatinControlador {
    private final MonopatinServicio monopatinServicio;

    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/latitud/{latitud}/longitud/{longitud}/rango/{rango}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> getMonopatinesCercanos(@PathVariable Double latitud, @PathVariable Double longitud, @PathVariable Double rango){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.getMonopatinesCercanos(latitud, longitud, rango));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/kilometros")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerOrdenadosPorKilometros(@RequestParam(name = "orden", required = false) String orden){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerOrdenadosPorKilometros(orden));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/tiempos/conPausas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerOrdenadosPorTiempoConPausas(@RequestParam(name = "orden", required = false) String orden){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerOrdenadosPorTiempoConPausas(orden));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/tiempos/sinPausas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerOrdenadosPorTiempoSinPausas(@RequestParam(name = "orden", required = false) String orden){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerOrdenadosPorTiempoSinPausas(orden));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerPorId(@PathVariable("id") Long id_monopatin){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerPorId(id_monopatin));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> crearMonopatin(@RequestBody Monopatin monopatin){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.crear(monopatin));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.eliminar(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    */

    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> editarMonopatin(@PathVariable("id") Long idMonopatin, @RequestBody Monopatin nuevaInfo){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.editar(idMonopatin, nuevaInfo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
