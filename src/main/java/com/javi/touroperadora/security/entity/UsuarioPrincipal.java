package com.javi.touroperadora.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {
    private String nombreUsuario;
    private String emailUsuario;
    private String passUsuario;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(String nombreUsuario, String emailUsuario, String passUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.passUsuario = passUsuario;
        this.authorities = authorities;
    }

    // con este método asigno los privilegios a cada usuario || convierto un usuario(de la bd) en usuarioPrincipal que es el que uso para saber los privilegios
    public static UsuarioPrincipal build (Usuario usuario) {
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList()); //convierto rol en authoritys
        return new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getEmailUsuario(), usuario.getPassUsuario(), authorities);// devuelvo usuario principal con los campos del usuario con las authorities
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passUsuario;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }
}
