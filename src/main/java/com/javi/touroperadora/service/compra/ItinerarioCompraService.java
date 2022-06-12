package com.javi.touroperadora.service.compra;

import com.javi.touroperadora.dto.compra.ItinerarioCompraDto;
import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.compra.ItinerarioCompra;
import com.javi.touroperadora.repository.compra.ItinerarioCompraRepository;
import com.javi.touroperadora.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItinerarioCompraService {
    @Autowired
    ItinerarioCompraRepository itinerarioCompraRepository;
    @Autowired
    ActividadService actividadService;

    //obtengo todos
    public List<ItinerarioCompra> list(){
        return itinerarioCompraRepository.findAll();
    }

    //obtengo 1 itinerario por id
    public Optional<ItinerarioCompra> getOne (int itinerarioId) {
        return itinerarioCompraRepository.findById(itinerarioId);
    }

    //obtengo 1 itinerario por nombre
    public Optional<ItinerarioCompra> getByNombre (String nombreHotel){
        return itinerarioCompraRepository.findByNombreHotel(nombreHotel);
    }

    //guardo itinerario
    public void save (ItinerarioCompra itinerario) {
        itinerarioCompraRepository.save(itinerario);
    }
    //elimino 1 itinerario
    public void delete (int itinerarioId) {
        itinerarioCompraRepository.deleteById(itinerarioId);
    }
    //compruebo si existe por id
    public boolean existsById (int itinerarioId){
        return itinerarioCompraRepository.existsById(itinerarioId);
    }

    //compruebo si existe por nombre
    public boolean existsByNombre(String nombreHotel){
        return itinerarioCompraRepository.existsByNombreHotel(nombreHotel);
    }

   // @Override
    public ItinerarioCompraDto addActividadAlItinerario (int actividadId , int itinerarioId){
        ItinerarioCompra itinerarioCompra = getOne(itinerarioId).get();
        Actividad actividad = actividadService.getByActividadId(actividadId).get();
        if(itinerarioCompra.getActividades().contains(actividad)){
            throw new IllegalArgumentException("esta actividad ya fue asignada a este itinerario");
        }
        itinerarioCompra.addActividad(actividad);
        //actividad.addItinerario(itinerario);

        return null;
    }
 /*
    public ItinerarioCompraDto removeActividadDelItinerario (int actividadId , int itinerarioId){
        ItinerarioCompra itinerarioCompra = getOne(itinerarioId).get();
        ActividadCompra actividadCompra = actividadCompraService.getByActividadId(actividadId).get();
        if(!(itinerarioCompra.getActividades().contains(actividadCompra))){
            throw new IllegalArgumentException("esta actividad no esta asignada a este itinerario");
        }
        itinerarioCompra.removeActividad(actividadCompra);
        //actividad.removeItinerario(itinerario);

        return null;
    }

  */
}
