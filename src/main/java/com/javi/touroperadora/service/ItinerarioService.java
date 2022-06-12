package com.javi.touroperadora.service;

import com.javi.touroperadora.controller.ImagenController;
import com.javi.touroperadora.dto.ItinerarioDto;
import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.Imagen;
import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ItinerarioService {
    @Autowired
    ItinerarioRepository itinerarioRepository;
    @Autowired
    ActividadService actividadService;

    @Autowired
    ImagenService imagenService;

    //obtengo todos
    public List<Itinerario> list(){
        return itinerarioRepository.findAll();
    }

    //obtengo todos filtro

    @Query(value = "select itinerarioId,nombreHotel,imagen,precioNoche,cordenadasHotel,plazas from Itinerario where plazas > 0")
    public List<Itinerario> list2(){
        return itinerarioRepository.findAll();
    }
    //obtengo 1 itinerario por id
    public Optional<Itinerario> getOne (int itinerarioId) {
        return itinerarioRepository.findById(itinerarioId);
    }

    //obtengo 1 itinerario por nombre
    public Optional<Itinerario> getByNombre (String nombreHotel){
        return itinerarioRepository.findByNombreHotel(nombreHotel);
    }

    //guardo itinerario
    public void save (Itinerario itinerario) {
        itinerarioRepository.save(itinerario);
    }
    //elimino 1 itinerario
    public void delete (int itinerarioId) {
        itinerarioRepository.deleteById(itinerarioId);
    }
    //compruebo si existe por id
    public boolean existsById (int itinerarioId){
        return itinerarioRepository.existsById(itinerarioId);
    }

    //compruebo si existe por nombre
    public boolean existsByNombre(String nombreHotel){
        return itinerarioRepository.existsByNombreHotel(nombreHotel);
    }

   // @Override
    public ItinerarioDto addActividadAlItinerario (int actividadId , int itinerarioId){
        Itinerario itinerario = getOne(itinerarioId).get();
        Actividad actividad = actividadService.getByActividadId(actividadId).get();
        if(itinerario.getActividades().contains(actividad)){
            throw new IllegalArgumentException("esta actividad ya fue asignada a este itinerario");
        }
        itinerario.addActividad(actividad);
        //actividad.addItinerario(itinerario);

        return null;
    }

    public ItinerarioDto removeActividadDelItinerario (int actividadId , int itinerarioId){
        Itinerario itinerario = getOne(itinerarioId).get();
        Actividad actividad = actividadService.getByActividadId(actividadId).get();
        if(!(itinerario.getActividades().contains(actividad))){
           throw new IllegalArgumentException("esta actividad no esta asignada a este itinerario");
        }
        itinerario.removeActividad(actividad);
        //actividad.removeItinerario(itinerario);

        return null;
    }

    public ItinerarioDto addImagenAlItinerario (Long imagenId , int itinerarioId) throws IOException {
        Itinerario itinerario = getOne(itinerarioId).get();
        Imagen imagen = imagenService.getByImagenId(imagenId).get();
        if(itinerario.getImagen() == imagen){
            throw new IllegalArgumentException("esta imagen ya fue asignada a este itinerario");
        }
        itinerario.addImagen(imagen);
        //actividad.addItinerario(itinerario);

        return null;
    }

    @Transactional
    public Itinerario removeImagenFromItinerario(Integer itinerarioId) {
        Itinerario itinerario = getOne(itinerarioId).get();
        if (!Objects.nonNull(itinerario.getImagen())) {
            throw new IllegalArgumentException("imagen no asignada a este itinerario");
        }
        itinerario.setImagen(null);
        return itinerario;
    }
}


