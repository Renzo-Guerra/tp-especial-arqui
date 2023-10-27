package com.example.microservicioparada.utils;

import com.example.microservicioparada.modelos.entidades.Parada;
import com.example.microservicioparada.repositorios.ParadaRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Component
public class CargarDatos {
    @Autowired
    private ParadaRespositorio paradaRepositorio;

    public void cargarDatosDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-parada/src/main/java/com/example/microservicioparada/csv/paradas.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Parada p = new Parada();
                p.setLatitud(Double.valueOf(csvRecord.get("latitud")));
                p.setLongitud(Double.valueOf(csvRecord.get("longitud")));
                p.setIsHabilitada(Boolean.valueOf(csvRecord.get("isHabilitada")));
                paradaRepositorio.save(p);
            }
        }
    }
}
