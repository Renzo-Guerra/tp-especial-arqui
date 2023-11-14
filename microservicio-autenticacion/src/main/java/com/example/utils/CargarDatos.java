package com.example.utils;

import com.example.entity.AuthUser;
import com.example.entity.Authority;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CargarDatos {
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userAuthRepository;
    private PasswordEncoder passwordEncoder;


    public void cargarAuthoritiesDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-autenticacion/src/main/java/com/example/csv/authorities.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Authority a = new Authority();
                a.setName(String.valueOf(csvRecord.get("name")));
                authorityRepository.save(a);
            }
        }
    }

    public void cargarUsuariosAuthDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-autenticacion/src/main/java/com/example/csv/users.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                AuthUser u = new AuthUser();
                u.setNombre(String.valueOf(csvRecord.get("name")));
                u.setApellido(String.valueOf(csvRecord.get("name")));
                u.setEmail(String.valueOf(csvRecord.get("name")));
                u.setPassword(passwordEncoder.encode(String.valueOf(csvRecord.get("name"))));
//                u.setAuthorities(Arrays.stream(String.valueOf(csvRecord.get("name")).split("_")).toList());
                userAuthRepository.save(u);
            }
        }
    }
}
