package com.javi.touroperadora.entity;

import lombok.Data;


import javax.persistence.*;
import java.util.*;

@Entity
@Data

public class Itinerario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itinerarioId;
    private String ciudad;
    private String cordenadasHotel;
    private String nombreHotel;
    private int precioNoche;
    private int plazas;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Imagen imagen;

    @ManyToMany( cascade = CascadeType.REFRESH , fetch = FetchType.LAZY  )
    @JoinTable(name= "itinerario_actividad",
    joinColumns = {@JoinColumn(name = "itinerario_id ")},
            inverseJoinColumns = {@JoinColumn(name = "actividad_id")})
    private List<Actividad> actividades =new ArrayList<>() ;

    public void addActividad (Actividad actividad){
        actividades.add(actividad);
    }

    public void addImagen (Imagen imagen){
        setImagen(imagen);
    }


    public void removeActividad (Actividad actividad){
        actividades.remove(actividad);
    }


    public Itinerario(String ciudad, String cordenadasHotel, String nombreHotel, int precioNoche, int plazas) {
       super();
        this.ciudad = ciudad;
        this.cordenadasHotel = cordenadasHotel;
        this.nombreHotel = nombreHotel;
        this.precioNoche = precioNoche;
        this.plazas = plazas;
    }

    public Itinerario() {

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

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}
