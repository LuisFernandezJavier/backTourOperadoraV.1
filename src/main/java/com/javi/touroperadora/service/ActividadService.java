package com.javi.touroperadora.service;

import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActividadService {
    @Autowired
    ActividadRepository actividadRepository;

    //obtaengo todas las actividades
    public List<Actividad> list(){return actividadRepository.findAll();}

    //obtengo 1 actividad por id
    public Optional<Actividad> getByActividadId (int actividadId) {
        return actividadRepository.findById(actividadId);
    }

    //obtengo 1 actividad por nombre
    public Optional<Actividad> getByNombre (String nombreActividad){
        return actividadRepository.findByNombreActividad(nombreActividad);
    }

    //guardo actividad
    public void save (Actividad actividad) {
        actividadRepository.save(actividad);
    }
    //elimino 1 itinerario
    public void delete (int actividadId) {
        actividadRepository.deleteById(actividadId);
    }
    //compruebo si existe por id
    public boolean existsById (int actividadId){
        return actividadRepository.existsById(actividadId);
    }

    //compruebo si existe por nombre
    public boolean existsByNombre(String nombreActividad){
        return actividadRepository.existsByNombreActividad(nombreActividad);
    }
}
