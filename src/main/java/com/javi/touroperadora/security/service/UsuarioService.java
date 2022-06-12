package com.javi.touroperadora.security.service;

import com.javi.touroperadora.entity.compra.ItinerarioCompra;
import com.javi.touroperadora.security.dto.UsuarioDto;
import com.javi.touroperadora.security.entity.Usuario;
import com.javi.touroperadora.security.repository.UsuarioRepository;
import com.javi.touroperadora.service.ItinerarioService;
import com.javi.touroperadora.service.compra.ItinerarioCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //para mantener coherencia en la bd
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ItinerarioCompraService itinerarioCompraService;

    public List<Usuario> list(){ return usuarioRepository.findAll();}
    public Optional<Usuario> getByNombreUsuario (String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    public Optional<Usuario> getOne (int usuarioId ) {
        return usuarioRepository.findById(usuarioId);
    }
    public boolean existsByNombreUsuario (String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    public boolean existsByEmailUsuario (String emailUsuario){
        return usuarioRepository.existsByEmailUsuario(emailUsuario);
    }
    public void save (Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public UsuarioDto addItinerarioAlUsuario (int itinerarioId , int usuarioId){
        Usuario usuario = getOne(usuarioId).get();
        ItinerarioCompra itinerario = itinerarioCompraService.getOne(itinerarioId).get();
        if(usuario.getItinerarios().contains(itinerario)){
            throw new IllegalArgumentException(("este itinerario ya fue asignado a este usuario"));
        }
        usuario.addItinerario(itinerario);
        return null;
    }

    public UsuarioDto removeItinerarioAlUsuario (int itinerarioId , int usuarioId){
        Usuario usuario = getOne(usuarioId).get();
        ItinerarioCompra itinerario = itinerarioCompraService.getOne(itinerarioId).get();
        if (!(usuario.getItinerarios().contains(itinerario))){
            throw new IllegalArgumentException(("este itinerario no esta asignado a este usuario"));
        }
        usuario.removeItinerario(itinerario);
        return null;
    }

    public boolean existsById (int usuarioId){
        return usuarioRepository.existsById(usuarioId);
    }
    public void delete (int usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

}
