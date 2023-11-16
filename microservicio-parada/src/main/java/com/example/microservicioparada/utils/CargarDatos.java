package com.example.microservicioparada.utils;

import com.example.microservicioparada.modelos.entidades.Parada;
import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import com.example.microservicioparada.repositorios.ParadaMongoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Component
public class CargarDatos {
    @Autowired
    private ParadaMongoRepositorio paradaMongoRepositorio;

    public void cargarDatosDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-parada/src/main/java/com/example/microservicioparada/csv/paradas.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                ParadaMongo p = new ParadaMongo();
                p.setLatitud(Double.valueOf(csvRecord.get("latitud")));
                p.setLongitud(Double.valueOf(csvRecord.get("longitud")));
                p.setIsHabilitada(Boolean.valueOf(csvRecord.get("isHabilitada")));
                paradaMongoRepositorio.save(p);
            }
        }
    }
}
/**
 *  The bean 'paradaMongoRepositorio',
 *  defined in com.example.microservicioparada.repositorios.ParadaMongoRepositorio
 *  defined in @EnableMongoRepositories declared on MicroservicioParadaApplication,
 *  could not be registered.
 *  A bean with that name has already been
 *  defined in com.example.microservicioparada.repositorios.ParadaMongoRepositorio
 *  defined in @EnableMongoRepositories declared on MongoDbConfig
 *  and overriding is disabled.
 */