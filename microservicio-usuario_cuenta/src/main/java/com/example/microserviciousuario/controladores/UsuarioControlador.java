package com.example.microserviciousuario.controladores;

import com.example.microserviciousuario.modelos.DTOS.UsuarioCreacionDTO;
import com.example.microserviciousuario.modelos.entidades.Usuario;
import com.example.microserviciousuario.security.AuthorityConstants;
import com.example.microserviciousuario.servicios.UsuarioServicio;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Date;

@RestController
@Data
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    /**
     * Devuelve todos los usuarios
     */
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerTodos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.traerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Devuelve un usuario por ID
     */
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> traerPorId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Crea a un usuario
     */
    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreacionDTO usuario){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.crearUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Elimina a un usuario
     */
    @DeleteMapping("{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.eliminarUsuario(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Edita los datos de un usuario
     */
    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.editarUsuario(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }




    /**
     * Lo ideal en la vida real sería que la app que esté implementando este endpoint tenga la latitud
     * y longitud de las coordenadas del dispositivo electronico donde se está corriendo la app.
     * Nosotros directamente hacemos el endpoint donde se pasa la latitud y longitud donde se supone
     * que está el usuario actual, a su vez, se le pasa un valor el cual será el rango de cercania
     *
     * con respecto a la ubicacion actual del usuario
     */
    @GetMapping("/monopatinesCercanos/latitud/{latitud}/longitud/{longitud}/rango/{rango}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" , \"" + AuthorityConstants.ADMIN + "\")" )
    public ResponseEntity<?> getMonopatinesCercanos(@PathVariable Double latitud, @PathVariable Double longitud, @PathVariable Double rango, @RequestHeader(name = "Authorization") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.getMonopatinesCercanos(latitud, longitud, rango, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }


    /*@PostMapping("/viajes/usuario/{id_usuario}/cuenta/{id_cuenta}/monopatin/{id_monopatin}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Integer id_usuario, @PathVariable Integer id_cuenta, @PathVariable Integer id_monopatin) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.finalizarViaje(id_usuario, id_cuenta, id_monopatin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }*/
    /*@PostMapping("/viajes/{id_viaje}")
    public ResponseEntity<?> finalizarViaje(@PathVariable Integer id_viaje) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.finalizarViaje(id_viaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }
    */

}
