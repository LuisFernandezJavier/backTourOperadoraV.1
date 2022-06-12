package com.javi.touroperadora.security.controller;

import com.javi.touroperadora.dto.ItinerarioDto;
import com.javi.touroperadora.dto.Mensaje;
import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.security.dto.UsuarioDto;
import com.javi.touroperadora.security.entity.Rol;
import com.javi.touroperadora.security.entity.Usuario;
import com.javi.touroperadora.security.enums.RolNombre;
import com.javi.touroperadora.security.service.RolService;
import com.javi.touroperadora.security.service.UsuarioService;
import com.javi.touroperadora.service.compra.ItinerarioCompraService;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;

    @GetMapping ("/lista")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list ,  HttpStatus.OK);
    }
    //listo por id
    @GetMapping("/usuarioId/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int usuario_id) {
        if (!usuarioService.existsById(usuario_id))
            return new ResponseEntity(new Mensaje("Usuario no existe"), HttpStatus.NOT_FOUND);
        Usuario usuario = usuarioService.getOne(usuario_id).get();
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    //busco por nombre
    @GetMapping("/detalleUsuario/{nombreUsuario}")
    public ResponseEntity<Usuario> getByNombreUsuario(@PathVariable("nombreUsuario") String nombre_usuario){
        if(!usuarioService.existsByNombreUsuario(nombre_usuario))
            return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        Usuario usuario = usuarioService.getByNombreUsuario(nombre_usuario).get();
        return new ResponseEntity<>(usuario , HttpStatus.OK);
    }

    @PostMapping ("addItinerario/{itinerarioId}/al/{usuarioId}")
    public ResponseEntity<?> addItinerario (@PathVariable final int itinerarioId,
                                                        @PathVariable final int usuarioId){
        UsuarioDto usuarioDto = usuarioService.addItinerarioAlUsuario(itinerarioId,usuarioId);
        return new ResponseEntity<>(new Mensaje("Itinerario asignado"), HttpStatus.OK);
    }

    @PostMapping ("removeItinerario/{itinerarioId}/al/{usuarioId}")
    public ResponseEntity<?> removeItinerario (@PathVariable final int itinerarioId,
                                            @PathVariable final int usuarioId){
        UsuarioDto usuarioDto = usuarioService.removeItinerarioAlUsuario(itinerarioId,usuarioId);
        return new ResponseEntity<>(new Mensaje("Itinerario removido"), HttpStatus.OK);
    }

    //elimino un usuario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/eliminoUsuario/{id}")
    public ResponseEntity<?> delete (@PathVariable ("id") int usuarioId) {
        if (!usuarioService.existsById(usuarioId))
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        usuarioService.delete(usuarioId);
        return new ResponseEntity<>(new Mensaje("Usuario borrado"), HttpStatus.OK);
    }

    // edito un usuario
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editoUsuario/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int usuarioId, @RequestBody UsuarioDto usuarioDto) {
        if (!usuarioService.existsById(usuarioId))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (usuarioService.existsByNombreUsuario(usuarioDto.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe o es el mismo"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(usuarioDto.getNombreUsuario()))
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);


        Usuario usuario = usuarioService.getOne(usuarioId).get();
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(usuarioDto.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Usuario actualizado"), HttpStatus.OK);
    }
}
