package com.javi.touroperadora.controller;

import com.javi.touroperadora.dto.ActividadDto;
import com.javi.touroperadora.dto.ItinerarioDto;
import com.javi.touroperadora.dto.Mensaje;
import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.service.ActividadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actividad")
@CrossOrigin(origins = "http://localhost:4200")
public class ActividadController {
    @Autowired
    ActividadService actividadService;
    // listo todas las actividades
    @GetMapping("/lista")
    public ResponseEntity<List<Actividad>> list() {
        List<Actividad> list = actividadService.list();
        return new ResponseEntity<List<Actividad>>(list, HttpStatus.OK);
    }

    //listo por id
    @GetMapping("/detalleActividad/{id}")
    public ResponseEntity<Actividad> getById(@PathVariable("id") int actividad_id) {
        if (!actividadService.existsById(actividad_id))
            return new ResponseEntity(new Mensaje("actividad no existe"), HttpStatus.NOT_FOUND);
        Actividad actividad = actividadService.getByActividadId(actividad_id).get();
        return new ResponseEntity<Actividad>(actividad, HttpStatus.OK);
    }

    //elimino una actividad
    @DeleteMapping ("/eliminoActividad/{id}")
    public ResponseEntity<?> delete (@PathVariable ("id") int actividadId) {
        if (!actividadService.existsById(actividadId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        actividadService.delete(actividadId);
        return new ResponseEntity<>(new Mensaje("actividad borrada"), HttpStatus.OK);
    }

    //creo una actividad

    @PostMapping("/crearActividad")
    public ResponseEntity<?> create(@RequestBody ActividadDto actividadDto) {
        if (StringUtils.isBlank(actividadDto.getNombreActividad()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (actividadDto.getPrecioActividad() < 0)
            return new ResponseEntity<>(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if (actividadService.existsByNombre(actividadDto.getNombreActividad()))
            return new ResponseEntity<>(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Actividad actividad = new Actividad(actividadDto.getNombreActividad(),actividadDto.getPrecioActividad(), actividadDto.getDescripcionActividad() );
        actividadService.save(actividad);
        return new ResponseEntity<>(new Mensaje("actividad creada"), HttpStatus.OK);
    }


    // edito una actividad
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editoActividad/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int actividadId, @RequestBody ActividadDto actividadDto) {
        if (!actividadService.existsById(actividadId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (actividadService.existsByNombre(actividadDto.getNombreActividad()) &&
                actividadService.getByNombre(actividadDto.getNombreActividad()).get().getActividadId() != actividadId)
            return new ResponseEntity(new Mensaje("ese nombre ya existe o es el mismo"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(actividadDto.getNombreActividad()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (actividadDto.getPrecioActividad() < 0)
            return new ResponseEntity<>(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Actividad actividad = actividadService.getByActividadId(actividadId).get();
        actividad.setNombreActividad(actividadDto.getNombreActividad());
        actividad.setPrecioActividad(actividadDto.getPrecioActividad());
        actividad.setDescripcionActividad(actividadDto.getDescripcionActividad());
        actividadService.save(actividad);
        return new ResponseEntity(new Mensaje("actividad actualizado"), HttpStatus.OK);
    }
}
