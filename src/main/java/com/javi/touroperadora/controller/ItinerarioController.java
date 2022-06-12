package com.javi.touroperadora.controller;

import com.javi.touroperadora.dto.ItinerarioDto;
import com.javi.touroperadora.dto.Mensaje;
import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.service.ActividadService;
import com.javi.touroperadora.service.ItinerarioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/itinerario")
@CrossOrigin(origins = "http://localhost:4200")
public class ItinerarioController {
    @Autowired
    ItinerarioService itinerarioService;
    @Autowired
    ActividadService actividadService;
    @Autowired
    ActividadController actividadController;

    //listo todos los itinerarios

    @GetMapping("/lista")
    public ResponseEntity<List<Itinerario>> list() {
        List<Itinerario> list = itinerarioService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/lista2")
    public ResponseEntity<List<Itinerario>> list2() {
        List<Itinerario> list = itinerarioService.list();

        List<Itinerario> list2 = new ArrayList<>();
        for (Itinerario itinerario : list){
            //System.out.println(itinerario);
            if (itinerario.getPlazas() > 0  && itinerario.getImagen() != null){

                list2.add(itinerario);
            }
        }
        return new ResponseEntity<>(list2, HttpStatus.OK);
    }

    //listo por id
    @GetMapping("/detalleItinerario/{id}")
    public ResponseEntity<Itinerario> getById(@PathVariable("id") int itinerario_id) {
        if (!itinerarioService.existsById(itinerario_id))
            return new ResponseEntity(new Mensaje("itinerario no existe"), HttpStatus.NOT_FOUND);
        Itinerario itinerario = itinerarioService.getOne(itinerario_id).get();
        return new ResponseEntity<Itinerario>(itinerario, HttpStatus.OK);
    }


    //creo un itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearItinerario")
    public ResponseEntity<?> create(@RequestBody ItinerarioDto itinerarioDto) {
        if (StringUtils.isBlank(itinerarioDto.getNombreHotel()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (itinerarioDto.getPrecioNoche() < 0)
            return new ResponseEntity<>(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if (itinerarioService.existsByNombre(itinerarioDto.getNombreHotel()))
            return new ResponseEntity<>(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Itinerario itinerario = new Itinerario(itinerarioDto.getCiudad(), itinerarioDto.getCordenadasHotel(), itinerarioDto.getNombreHotel(), itinerarioDto.getPrecioNoche(),itinerarioDto.getPlazas());

        itinerarioService.save(itinerario);

        return new ResponseEntity<>(new Mensaje("itinerario creado"), HttpStatus.OK);
    }

    // edito un itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editoItinerario/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int itinerarioId, @RequestBody ItinerarioDto itinerarioDto) {
        if (!itinerarioService.existsById(itinerarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (itinerarioService.existsByNombre(itinerarioDto.getNombreHotel()) &&
                itinerarioService.getByNombre(itinerarioDto.getNombreHotel()).get().getItinerarioId() != itinerarioId)
            return new ResponseEntity(new Mensaje("ese nombre ya existe o es el mismo"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(itinerarioDto.getNombreHotel()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (itinerarioDto.getPrecioNoche() < 0)
            return new ResponseEntity<>(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Itinerario itinerario = itinerarioService.getOne(itinerarioId).get();
        itinerario.setCiudad(itinerarioDto.getCiudad());
        itinerario.setNombreHotel(itinerarioDto.getNombreHotel());
        itinerario.setCordenadasHotel(itinerarioDto.getCordenadasHotel());
        itinerario.setPrecioNoche(itinerarioDto.getPrecioNoche());
        itinerario.setPlazas(itinerarioDto.getPlazas());
        itinerarioService.save(itinerario);
        return new ResponseEntity(new Mensaje("itinerario actualizado"), HttpStatus.OK);
    }

    //elimino un itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/eliminoItinerario/{id}")
    public ResponseEntity<?> delete (@PathVariable ("id") int itinerarioId) {
        if (!itinerarioService.existsById(itinerarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        itinerarioService.delete(itinerarioId);
        return new ResponseEntity<>(new Mensaje("itinerario borrado"), HttpStatus.OK);
    }

    //relaciono itinerario con sus actividades
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/addActividad/{actividadId}/al/{itinerarioId}")
    public ResponseEntity<?>  addActividad (@PathVariable final int actividadId ,
                                                       @PathVariable final int itinerarioId){
        ItinerarioDto itinerarioDto = itinerarioService.addActividadAlItinerario(actividadId , itinerarioId);
        return new ResponseEntity<>(new Mensaje("actividad añadida"), HttpStatus.OK);
    }

    //remuevo actividad del itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/removeActividad/{actividadId}/del/{itinerarioId}")
    public ResponseEntity<?>  removeActividad (@PathVariable final int actividadId ,
                                            @PathVariable final int itinerarioId){
        ItinerarioDto itinerarioDto = itinerarioService.removeActividadDelItinerario(actividadId , itinerarioId);
        return new ResponseEntity<>(new Mensaje("actividad removida"), HttpStatus.OK);
    }
    //añado imagen al itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/addImagen/{imagenId}/al/{itinerarioId}")
    public ResponseEntity<?>  addImagen (@PathVariable final Long imagenId ,
                                            @PathVariable final int itinerarioId) throws IOException {
        ItinerarioDto itinerarioDto = itinerarioService.addImagenAlItinerario(imagenId, itinerarioId);
        return new ResponseEntity<>(new Mensaje("imagen añadida"), HttpStatus.OK);
    }

    //elimino imagen al itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteImagen/{itinerarioId}")
    public ResponseEntity<Itinerario> deleteImagen (@PathVariable final Integer itinerarioId) {
        Itinerario itinerario = itinerarioService.removeImagenFromItinerario(itinerarioId);
        return new ResponseEntity<>(itinerario, HttpStatus.OK);
    }

    //gestion de plazas

    @PutMapping("/asignoPlazas/{id}")
    public ResponseEntity<?> updatePlazas (@PathVariable("id") int itinerarioId, @RequestBody ItinerarioDto itinerarioDto) {
        if (!itinerarioService.existsById(itinerarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);


        Itinerario itinerario = itinerarioService.getOne(itinerarioId).get();
        itinerario.setPlazas(itinerarioDto.getPlazas());
        itinerarioService.save(itinerario);
        return new ResponseEntity(new Mensaje("plazas actualizadas"), HttpStatus.OK);
    }


}
