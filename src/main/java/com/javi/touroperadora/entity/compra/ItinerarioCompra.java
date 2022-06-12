package com.javi.touroperadora.entity.compra;

import com.javi.touroperadora.entity.Actividad;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data

public class ItinerarioCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itinerarioId;
    private String ciudad;
    private String cordenadasHotel;
    private String nombreHotel;
    private int precioNoche;
    private int plazasCompradas;

    private Date fechaInicio;
    private Date fechaFinal;

    @ManyToMany( cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name= "itinerarioCompra_actividadCompra",
    joinColumns = {@JoinColumn(name = "itinerario_id ")},
            inverseJoinColumns = {@JoinColumn(name = "actividad_id")})
    private List<Actividad> actividades =new ArrayList<>() ;

    public void addActividad (Actividad actividad){
        actividades.add(actividad);
    }

    public void removeActividad (Actividad actividad){
        actividades.remove(actividad);
    }


    public ItinerarioCompra(String ciudad, String cordenadasHotel, String nombreHotel, int precioNoche, int plazasCompradas, Date fechaInicio, Date fechaFinal) {
        this.ciudad = ciudad;
        this.cordenadasHotel = cordenadasHotel;
        this.nombreHotel = nombreHotel;
        this.precioNoche = precioNoche;
        this.plazasCompradas = plazasCompradas;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public ItinerarioCompra() {

    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public int getItinerarioId() {
        return itinerarioId;
    }

    public void setItinerario_id(int itinerario_id) {
        this.itinerarioId = itinerario_id;
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
