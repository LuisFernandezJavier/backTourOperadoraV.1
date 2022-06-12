package com.javi.touroperadora.security.controller;

import com.javi.touroperadora.dto.Mensaje;
import com.javi.touroperadora.security.dto.JwtDto;
import com.javi.touroperadora.security.dto.LoginUsuario;
import com.javi.touroperadora.security.dto.NuevoUsuario;
import com.javi.touroperadora.security.entity.Rol;
import com.javi.touroperadora.security.entity.Usuario;
import com.javi.touroperadora.security.enums.RolNombre;
import com.javi.touroperadora.security.jwt.JwtProvider;
import com.javi.touroperadora.security.service.RolService;
import com.javi.touroperadora.security.service.UsuarioService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevoUsuario")
    public ResponseEntity<?> nuevo (@Valid @RequestBody NuevoUsuario nuevoUsuario , BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Mensaje("Campos o email invalidos ") , HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
        return new ResponseEntity<>(new Mensaje("Ese nombre ya existe") , HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByEmailUsuario(nuevoUsuario.getEmailUsuario()))
            return new ResponseEntity<>(new Mensaje("Ese nemail ya existe") , HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmailUsuario(), passwordEncoder.encode(nuevoUsuario.getPassUsuario()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }
 @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid  @RequestBody LoginUsuario loginUsuario , BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos invalidos") , HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassUsuario()));
        SecurityContextHolder.getContext().setAuthentication(authentication); // valido el usuario
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt , userDetails.getUsername() , userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto , HttpStatus.OK);
    }

}
