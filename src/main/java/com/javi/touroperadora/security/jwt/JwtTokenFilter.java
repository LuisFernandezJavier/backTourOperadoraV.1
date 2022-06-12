package com.javi.touroperadora.security.jwt;

import org.apache.catalina.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getToken(req);
            if (token != null && jwtProvider.validateToken(token)){
                String nombreUsuario =  jwtProvider.getNombreUsuarioFromToken(token); //extraigo nombre del token
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario); //cargo el usuario con ese nombre
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities()); //obtengo los permisos del usuario
                SecurityContextHolder.getContext().setAuthentication(auth); //al contexto de autorizacion le asigno el usuario

            }

        }catch (Exception e){
            logger.error("fallo en el metodo do filter");
        }
        filterChain.doFilter(req , res);
    }

    //le quito la cabecera al token
    private String getToken (HttpServletRequest request){
        String header = request.getHeader("Authorization") ;
        if (header != null&& header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
