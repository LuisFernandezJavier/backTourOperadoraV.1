package com.javi.touroperadora.dto;

import com.javi.touroperadora.entity.Actividad;
import com.javi.touroperadora.entity.Imagen;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItinerarioDto {
    private int itinerarioId;
    @NotBlank
    private String ciudad;
    private String cordenadasHotel;
    @NotBlank
    private String nombreHotel;
    @Min(0)
    private int precioNoche;

    private int plazas;

    private Imagen imagen;



    private List<Actividad> actividades ;


    public int getItinerarioId() {
        return itinerarioId;
    }

    public void setItinerarioId(int itinerarioId) {
        this.itinerarioId = itinerarioId;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCordenadasHotel() {
        return cordenadasHotel;
    }

    public void setCordenadasHotel(String cordenadasHotel) {
        this.cordenadasHotel = cordenadasHotel;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public int getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(int precioNoche) {
        this.precioNoche = precioNoche;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}
