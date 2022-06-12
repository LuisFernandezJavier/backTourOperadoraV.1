package com.javi.touroperadora.controller.compra;

import com.javi.touroperadora.dto.Mensaje;
import com.javi.touroperadora.dto.compra.ItinerarioCompraDto;
import com.javi.touroperadora.entity.compra.ItinerarioCompra;
import com.javi.touroperadora.service.compra.ItinerarioCompraService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itinerarioCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class ItinerarioCompraController {
    @Autowired
    ItinerarioCompraService itinerarioCompraService;




    //listo todos los itinerarios

    @GetMapping("/lista")
    public ResponseEntity<List<ItinerarioCompra>> list() {
        List<ItinerarioCompra> list = itinerarioCompraService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //listo por id
    @GetMapping("/detalleItinerario/{id}")
    public ResponseEntity<ItinerarioCompra> getById(@PathVariable("id") int itinerario_id) {
        if (!itinerarioCompraService.existsById(itinerario_id))
            return new ResponseEntity(new Mensaje("itinerario no existe"), HttpStatus.NOT_FOUND);
        ItinerarioCompra itinerarioCompra = itinerarioCompraService.getOne(itinerario_id).get();
        return new ResponseEntity<ItinerarioCompra>(itinerarioCompra, HttpStatus.OK);
    }

    // listo por nombre
    @GetMapping ("/detalleItinerarioName/{nombreHotel}")
    public ResponseEntity<ItinerarioCompra> getByNombreHotel(@PathVariable("nombreHotel") String nombre_hotel) {
        if (!itinerarioCompraService.existsByNombre(nombre_hotel))
            return new ResponseEntity(new Mensaje("El itinerario no existe"), HttpStatus.NOT_FOUND);
        ItinerarioCompra itinerarioCompra = itinerarioCompraService.getByNombre(nombre_hotel).get();
        return new ResponseEntity<ItinerarioCompra>(itinerarioCompra, HttpStatus.OK);
    }


    //creo un itinerario
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearItinerario")
    public ResponseEntity<?> create(@RequestBody ItinerarioCompraDto itinerarioCompraDto) {
        if (StringUtils.isBlank(itinerarioCompraDto.getNombreHotel()))
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (itinerarioCompraDto.getPrecioNoche() < 0)
            return new ResponseEntity<>(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        ItinerarioCompra itinerarioCompra = new ItinerarioCompra(itinerarioCompraDto.getCiudad(), itinerarioCompraDto.getCordenadasHotel(), itinerarioCompraDto.getNombreHotel(),
                itinerarioCompraDto.getPrecioNoche() ,itinerarioCompraDto.getPlazasCompradas() , itinerarioCompraDto.getFechaInicio(),itinerarioCompraDto.getFechaFinal() );

        itinerarioCompraService.save(itinerarioCompra);

        return new ResponseEntity<>( itinerarioCompra , HttpStatus.OK);
    }

    // edito un itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editoItinerario/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int itinerarioId, @RequestBody ItinerarioCompraDto itinerarioCompraDto) {
        if (!itinerarioCompraService.existsById(itinerarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (itinerarioCompraService.existsByNombre(itinerarioCompraDto.getNombreHotel()) &&
                itinerarioCompraService.getByNombre(itinerarioCompraDto.getNombreHotel()).get().getItinerarioId() != itinerarioId)
            return new ResponseEntity(new Mensaje("ese nombre ya existe o es el mismo"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(itinerarioCompraDto.getNombreHotel()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (itinerarioCompraDto.getPrecioNoche() < 0)
            return new ResponseEntity<>(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        ItinerarioCompra itinerarioCompra = itinerarioCompraService.getOne(itinerarioId).get();
        itinerarioCompra.setNombreHotel(itinerarioCompraDto.getNombreHotel());
        itinerarioCompra.setPrecioNoche(itinerarioCompraDto.getPrecioNoche());
        itinerarioCompraService.save(itinerarioCompra);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

    //elimino un itinerario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/eliminoItinerario/{id}")
    public ResponseEntity<?> delete (@PathVariable ("id") int itinerarioId) {
        if (!itinerarioCompraService.existsById(itinerarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        itinerarioCompraService.delete(itinerarioId);
        return new ResponseEntity<>(new Mensaje("itinerario borrado"), HttpStatus.OK);
    }

    //relaciono itinerario con sus actividades
    @PostMapping ("/addActividad/{actividadId}/al/{itinerarioId}")
    public ResponseEntity<?>  addActividad (@PathVariable final int actividadId ,
                                                       @PathVariable final int itinerarioId){
        ItinerarioCompraDto itinerarioCompraDto = itinerarioCompraService.addActividadAlItinerario(actividadId , itinerarioId);
        return new ResponseEntity<>(new Mensaje("actividad añadida"), HttpStatus.OK);
    }
    /*
    //relaciono itinerario con sus actividades
    @PostMapping ("/removeActividad/{actividadId}/del/{itinerarioId}")
    public ResponseEntity<?>  removeActividad (@PathVariable final int actividadId ,
                                            @PathVariable final int itinerarioId){
        ItinerarioCompraDto itinerarioCompraDto = itinerarioCompraService.removeActividadDelItinerario(actividadId , itinerarioId);
        return new ResponseEntity<>(new Mensaje("actividad removida"), HttpStatus.OK);
    }
    */

}
