package com.example.utils;

import com.example.entity.AuthUser;
import com.example.entity.Authority;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserRepository;
import com.example.service.exception.user.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CargarDatos {
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
    public void cargarUsuariosAuthDesdeCSV() throws IOException {
        File archivoCSV = ResourceUtils.getFile("microservicio-autenticacion/src/main/java/com/example/csv/users.csv");

        try (FileReader reader = new FileReader(archivoCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                AuthUser u = new AuthUser();
                u.setNombre(String.valueOf(csvRecord.get("nombre")));
                u.setApellido(String.valueOf(csvRecord.get("apellido")));
                u.setEmail(String.valueOf(csvRecord.get("email")));
                u.setPassword(passwordEncoder.encode(String.valueOf(csvRecord.get("password"))));
                List<Authority> autoridades = Arrays.stream(String.valueOf(csvRecord.get("authorities"))
                                                .split("_"))
                                                .map(autoridad -> this.authorityRepository.findByName( autoridad ).orElseThrow( () -> new NotFoundException("Autority", autoridad ) ) )
                                                .toList();
                u.setAuthorities(autoridades);
                userAuthRepository.save(u);
            }
        }
    }
}
