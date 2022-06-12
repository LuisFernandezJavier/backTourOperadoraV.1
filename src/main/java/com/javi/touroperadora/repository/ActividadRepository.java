package com.javi.touroperadora.repository;

import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    Optional<Actividad> findByNombreActividad (String nombreActividad);


    boolean existsByNombreActividad (String nombreActividad);
}
