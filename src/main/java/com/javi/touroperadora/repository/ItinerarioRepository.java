package com.javi.touroperadora.repository;

import com.javi.touroperadora.dto.ItinerarioDto;
import com.javi.touroperadora.entity.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItinerarioRepository extends JpaRepository <Itinerario,  Integer> {
//buscar por nombre
    Optional<Itinerario> findByNombreHotel (String nombreHotel);
    //verifica si existe por el nombre
    boolean existsByNombreHotel (String nombreHotel);





}
