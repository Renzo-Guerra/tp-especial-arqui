package com.example;
import com.example.utils.CargarDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AuthenticationMain {

    @Autowired
    private CargarDatos cargaDeDatos;

    public static void main(String[] args) {
        SpringApplication.run( AuthenticationMain.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarAuthoritiesDesdeCSV();
        cargaDeDatos.cargarUsuariosAuthDesdeCSV();
    }
}