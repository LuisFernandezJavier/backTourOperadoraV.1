package com.javi.touroperadora.repository;

import com.javi.touroperadora.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    Optional<Imagen> findByNombreImagen(String nombreImagen);

    @Override
    Optional<Imagen> findById(Long imagenId);
}
