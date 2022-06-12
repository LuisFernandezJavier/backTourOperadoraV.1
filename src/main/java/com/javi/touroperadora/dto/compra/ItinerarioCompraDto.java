package com.javi.touroperadora.dto.compra;

import com.javi.touroperadora.entity.Actividad;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class ItinerarioCompraDto {
    private int itinerarioId;
    @NotBlank
    private String ciudad;
    private String cordenadasHotel;
    @NotBlank
    private String nombreHotel;
    @Min(0)
    private int precioNoche;

    @NotBlank
    private Date fechaInicio;
    @NotBlank
    private Date fechaFinal;

    private int plazasCompradas;

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

    public int getPlazasCompradas() {
        return plazasCompradas;
    }

    public void setPlazasCompradas(int plazasCompradas) {
        this.plazasCompradas = plazasCompradas;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
