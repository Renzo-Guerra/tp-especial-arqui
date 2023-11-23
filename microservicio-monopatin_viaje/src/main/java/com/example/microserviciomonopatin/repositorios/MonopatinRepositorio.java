package com.example.microserviciomonopatin.repositorios;

import com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento;
import com.example.microserviciomonopatin.modelos.entidades.Monopatin;
import com.example.microserviciomonopatin.modelos.DTOS.MonopatinKilometrajeDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepositorio extends CrudRepository<Monopatin, Long> {

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinKilometrajeDTO(m.id_monopatin, SUM(v.km_recorridos)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY SUM(v.km_recorridos) ASC
            """)
    List<MonopatinKilometrajeDTO> traerOrdenadosPorKilometrosASC();

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinKilometrajeDTO(m.id_monopatin,  SUM(v.km_recorridos)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY SUM(v.km_recorridos) DESC
            """)
    List<MonopatinKilometrajeDTO> traerOrdenadosPorKilometrosDESC();

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento(m.id_monopatin, LONG(SUM(v.fin) - SUM(v.inicio)), LONG((SUM(v.fin) - SUM(v.inicio)) - SUM(v.segundos_estacionado)), SUM(v.segundos_estacionado)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY (SUM(v.fin) - SUM(v.inicio)) DESC
            """)
    List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoConPausasDESC();

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento(m.id_monopatin, LONG(SUM(v.fin) - SUM(v.inicio)), LONG((SUM(v.fin) - SUM(v.inicio)) - SUM(v.segundos_estacionado)), SUM(v.segundos_estacionado)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY (SUM(v.fin) - SUM(v.inicio)) ASC
            """)
    List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoConPausasASC();

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento(m.id_monopatin, LONG(SUM(v.fin) - SUM(v.inicio)), LONG((SUM(v.fin) - SUM(v.inicio)) - SUM(v.segundos_estacionado)), SUM(v.segundos_estacionado)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY (SUM(v.fin) - SUM(v.inicio) - SUM(v.segundos_estacionado)) DESC
            """)
    List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoSinPausasDESC();

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.MonopatinTiempoFuncionamiento(
                m.id_monopatin, 
                CAST(SUM(v.fin) - SUM(v.inicio) AS LONG), 
                CAST(SUM(v.fin) - SUM(v.inicio) - SUM(v.segundos_estacionado) AS LONG), 
                CAST(SUM(v.segundos_estacionado) AS LONG)) 
            FROM Viaje v
            JOIN Monopatin m ON m.id_monopatin = v.id_monopatin
            WHERE v.fin IS NOT NULL
            ORDER BY (SUM(v.fin) - SUM(v.inicio) - SUM(v.segundos_estacionado)) ASC
            """)
    List<MonopatinTiempoFuncionamiento> traerOrdenadosPorTiempoSinPausasASC();

    @Query("""
            SELECT m 
            FROM Monopatin m 
            WHERE m.estado = 'disponible' AND 
               (m.latitud BETWEEN :latitudMinima AND :latitudMaxima) AND 
                (m.longitud BETWEEN :longitudMinima AND :longitudMaxima)""")
    Iterable<Monopatin> getMonopatinesCercanos(Double latitudMinima, Double latitudMaxima, Double longitudMinima, Double longitudMaxima);
}
