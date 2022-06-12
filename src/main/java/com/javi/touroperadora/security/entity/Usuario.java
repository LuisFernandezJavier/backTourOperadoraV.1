package com.javi.touroperadora.security.entity;

import com.javi.touroperadora.entity.Itinerario;
import com.javi.touroperadora.entity.compra.ItinerarioCompra;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int usuarioId;
    @NotNull
    @Column (unique = true)
    private String nombreUsuario;
    @NotNull
    private String emailUsuario;
    @NotNull
    @Column (unique = true)
    private String passUsuario;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol" , joinColumns = @JoinColumn (name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles= new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_itinerario",
    joinColumns = {@JoinColumn(name = "usuario_id")},
            inverseJoinColumns =  {@JoinColumn(name="itinerario_id")})
    private List<ItinerarioCompra> itinerarios = new ArrayList<>();

    public void addItinerario (ItinerarioCompra itinerarioCompra) {itinerarios.add(itinerarioCompra);}

    public void removeItinerario (ItinerarioCompra itinerarioCompra) {itinerarios.remove(itinerarioCompra);}

    public Usuario( ) {

    }

    public Usuario(String nombreUsuario, String emailUsuario, String passUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.passUsuario = passUsuario;

    }

    public List<ItinerarioCompra> getItinerarios() {
        return itinerarios;
    }

    public void setItinerarios(List<ItinerarioCompra> itinerarios) {
        this.itinerarios = itinerarios;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
