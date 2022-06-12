package com.javi.touroperadora.repository.compra;

import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.entity.compra.ItinerarioCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItinerarioCompraRepository extends JpaRepository <ItinerarioCompra,  Integer> {
//buscar por nombre
    Optional<ItinerarioCompra> findByNombreHotel (String nombreHotel);
    boolean existsByNombreHotel (String nombreHotel);

}
