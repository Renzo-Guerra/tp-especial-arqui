package com.example.microserviciomonopatin.utils;

import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.repositorios.MonopatinRespositorio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CargarDatos {
    @Autowired
    private MonopatinRespositorio monopatinRepositorio;

    public void cargarDatosDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-monopatin_viaje/src/main/java/com/example/microserviciomonopatin/csv/monopatines.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Monopatin m = new Monopatin();
                m.setLatitud(Double.valueOf(csvRecord.get("latitud")));
                m.setLongitud(Double.valueOf(csvRecord.get("longitud")));
                m.setEstado(csvRecord.get("estado"));
                m.setGps(Long.valueOf(csvRecord.get("gps")));
                monopatinRepositorio.save(m);
            }
        }
    }
}
