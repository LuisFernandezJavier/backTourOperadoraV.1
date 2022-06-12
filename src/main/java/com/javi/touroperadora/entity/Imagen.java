package com.javi.touroperadora.entity;

import javax.persistence.*;

@Entity
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imagenId;

    private String nombreImagen;

    private String tipoImagen;

    @Column( length = 10000000)
    private byte[] imagenByte;

    public Imagen() {
        super();
    }

    public Imagen(String nombreImagen, String tipoImagen, byte[] imagenByte) {
        this.nombreImagen = nombreImagen;
        this.tipoImagen = tipoImagen;
        this.imagenByte = imagenByte;
    }

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public byte[] getImagenByte() {
        return imagenByte;
    }

    public void setImagenByte(byte[] imagenByte) {
        this.imagenByte = imagenByte;
    }
}
