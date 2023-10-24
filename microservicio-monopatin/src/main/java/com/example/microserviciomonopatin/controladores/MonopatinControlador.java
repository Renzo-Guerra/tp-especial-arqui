package com.example.microserviciomonopatin.controladores;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.repositorios.MonopatinRespositorio;
import com.example.microserviciomonopatin.servicios.MonopatinServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/monopatines")
public class MonopatinControlador {

    private final MonopatinServicio monopatinServicio;

    @GetMapping("")
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable("id") Long id_monopatin){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.traerPorId(id_monopatin));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> crearMonopatin(@RequestBody Monopatin monopatin){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.crear(monopatin));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No se pudieron recuperar los datos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.eliminar(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMonopatin(@PathVariable("id") Long idMonopatin, @RequestBody Monopatin nuevaInfo){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(monopatinServicio.editar(idMonopatin, nuevaInfo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
