package com.javi.touroperadora.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ActividadDto {

    @NotBlank
    private String nombreActividad;

    @NotBlank
    @Min(0)
    private int precioActividad;

    private String descripcionActividad;

    public ActividadDto(String nombreActividad, int precioActividad, String descripcionActividad) {
        this.nombreActividad = nombreActividad;
        this.precioActividad = precioActividad;
        this.descripcionActividad = descripcionActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public int getPrecioActividad() {
        return precioActividad;
    }

    public void setPrecioActividad(int precioActividad) {
        this.precioActividad = precioActividad;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }
}
