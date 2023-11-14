package com.example.microservicioparada.controladores;

import com.example.microservicioparada.modelos.entidades.Parada;
import com.example.microservicioparada.security.AuthorityConstants;
import com.example.microservicioparada.servicios.ParadaServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/paradas")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ParadaControlador {
    private final ParadaServicio paradaServicio;

    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.traerTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> traerPorId(@PathVariable("id") Long id_parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.traerPorId(id_parada));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/buscarParada/latitud/{latitud}/longitud/{longitud}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> buscarParadaPorCoordenadas(@PathVariable("latitud") Double latitud, @PathVariable("longitud") Double longitud){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.buscarParadaPorCoordenadas(latitud, longitud));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> crearParada(@RequestBody Parada parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.crear(parada));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarParada(@PathVariable("id") Long id_parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.eliminar(id_parada));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> editarParada(@PathVariable("id") Long id_parada, @RequestBody Parada nuevaInfo){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(paradaServicio.editar(id_parada, nuevaInfo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
