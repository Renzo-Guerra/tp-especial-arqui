package com.example.microservicioparada;

import com.example.microservicioparada.utils.CargarDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication
public class MicroservicioParadaApplication {

	@Autowired
	private CargarDatos cargaDeDatos;
	public static void main(String[] args) {
		SpringApplication.run(MicroservicioParadaApplication.class, args);
	}

	@PostConstruct
	public void init() throws IOException {
		cargaDeDatos.cargarDatosDesdeCSV();
	}


}
