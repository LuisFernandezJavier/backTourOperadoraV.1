package com.javi.touroperadora.service;

import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.Imagen;
import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImagenService {
    @Autowired
    ImagenRepository imagenRepository;

    //busco 1 actividad id
    public Optional<Imagen> getByImagenId (Long imagenId) {
        return imagenRepository.findById(imagenId);
    }
    public List<Imagen> list(){
        return imagenRepository.findAll();
    }



}
