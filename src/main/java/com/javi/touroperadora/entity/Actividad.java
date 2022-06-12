package com.javi.touroperadora.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actividadId ;

    @NotNull
    private String nombreActividad;

    @NotNull
    private int precioActividad;

    private String descripcionActividad;


    public Actividad() {
    }


    public Actividad(String nombreActividad, int precioActividad, String descripcionActividad) {
        this.nombreActividad = nombreActividad;
        this.precioActividad = precioActividad;
        this.descripcionActividad = descripcionActividad;
    }
/*
    public List<Itinerario> getItinerarios() {
        return itinerarios;
    }

    public void setItinerarios(List<Itinerario> itinerarios) {
        this.itinerarios = itinerarios;
    } */

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
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
