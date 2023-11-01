package com.example.microserviciousuario.utils;


import com.example.microserviciousuario.modelos.DTOS.UsuarioCuentaDTO;
import com.example.microserviciousuario.modelos.entidades.Cuenta;
import com.example.microserviciousuario.modelos.entidades.Usuario;
import com.example.microserviciousuario.repositorios.CuentaRepositorio;
import com.example.microserviciousuario.repositorios.UsuarioRepositorio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CargarDatos {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private CuentaRepositorio cuentaRepositorio;
    public void cargarDatosDesdeCSV() throws IOException {

        File archivoCSV = ResourceUtils.getFile("microservicio-usuario_cuenta/src/main/java/com/example/microserviciousuario/csv/usuarios.csv");
        File archivoCSV2 = ResourceUtils.getFile("microservicio-usuario_cuenta/src/main/java/com/example/microserviciousuario/csv/cuentas.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Usuario u = new Usuario();
                u.setNombre(csvRecord.get("nombre"));
                u.setApellido(csvRecord.get("apellido"));
                u.setFecha_alta(LocalDateTime.now());
                usuarioRepositorio.save(u);
            }
        }

        try (FileReader reader2 = new FileReader(archivoCSV2);
             CSVParser csvParser2 = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader2)) {

            for (CSVRecord csvRecord : csvParser2) {
                Cuenta c = new Cuenta();
                c.setId_mercado_pago(Long.valueOf(csvRecord.get("id_mercado_pago")));
                c.setSaldo(Double.valueOf(csvRecord.get("saldo")));
                c.setIsHabilitada(Boolean.valueOf(csvRecord.get("isHabilitada")));
                c.setFecha_alta(LocalDateTime.now());
                cuentaRepositorio.save(c);
            }
        }
        // Traemos todos los usuarios
        Iterable<Usuario> usuarios = this.usuarioRepositorio.findAll();
        // Por cada usuario traemos una cuenta y agregamos el usuario a la cuenta
        for (Usuario usuario : usuarios) {
            Long id_usuario = usuario.getId();
            // Traemos las cuenta en base a un id pero a su vez nos traemos la lista de usuarios asignada a la cuenta
            Optional<Cuenta> cuenta = this.cuentaRepositorio.findByIdWithCuentas(id_usuario);
            if (cuenta.isPresent()) {
                System.out.println("Cuenta " + id_usuario + ": ");
                System.out.println(cuenta.get());
                // Agregamos el usuario a la cuenta
                cuenta.get().agregarUsuario(usuario);
                // Volvemos a guardar la cuenta (ya con el usuario cargado)
                this.cuentaRepositorio.save(cuenta.get());
            }
        }
    }
}
