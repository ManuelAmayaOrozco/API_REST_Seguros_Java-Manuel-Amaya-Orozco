package com.es.segurosinseguros.Controller;

import com.es.segurosinseguros.DTO.UsuarioLoginDTO;
import com.es.segurosinseguros.DTO.UsuarioRegisterDTO;
import com.es.segurosinseguros.Service.CustomUserDetailsService;
import com.es.segurosinseguros.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    /**
     * El objeto authenticationManager de tipo AuthenticationManager es un Bean que viene inyectado por SpringBoot
     * ¿De dónde sale la inicialización de este objeto?
     * La inicialización de este objeto viene dada en la clase com.es.jwtsecurity.security.SecurityConfig,
     * más concretamente en el método com.es.jwtsecurity.security.SecurityConfig#authenticationManager(AuthenticationConfiguration)
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * El objeto customUserDetailsService es un objeto de tipo {@link CustomUserDetailsService}
     * ¡RECORDAD!
     * La clase CustomUserDetailsService no es más que nuestro UsuarioService pero con otro nombre.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(
            @RequestBody UsuarioLoginDTO usuarioLoginDTO
    ) {

        /*
        Recordamos de las diapositivas de clase:
        - Authentication manager es una interfaz que tiene 1 único método (authenticate()), y que con ese método
        se pueden hacer 3 cosas: Devolver un objeto de tipo Authentication, lanzar una excepción de tipo AuthenticationException
        o devolver null
        - AuthenticationManager sirve para realizar la autenticación (autenticarse)
        - El objeto de tipo Authentication contendrá la siguiente información
            1. El usuario que se ha autenticado
            2. Las credenciales del usuario
            3. Los permisos del usuario
            4. Las autorizaciones del usuario
            5. Otros detalles adicionales
         */

        System.out.println(
                usuarioLoginDTO.getUsername() + " " + usuarioLoginDTO.getPassword()
        );

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword())// modo de autenticación
            );
        } catch (Exception e) {
            System.out.println("Excepcion en authentication");
            e.printStackTrace();
        }

        // Generamos el token
        String token = "";
        try {
            token = tokenService.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("Excepcion en generar token");
            e.printStackTrace();
        }

        // Retornamos el token
        return token;

    }


    /**
     * El proceso de registro de un usuario es exactamente igual que siempre aquí en el {@link UsuarioController}
     * @param usuarioRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioRegisterDTO> register(
            @RequestBody UsuarioRegisterDTO usuarioRegisterDTO) {

        System.out.println(
                usuarioRegisterDTO.getPassword()
        );

        customUserDetailsService.registerUser(usuarioRegisterDTO);

        return new ResponseEntity<UsuarioRegisterDTO>(usuarioRegisterDTO, HttpStatus.OK);

    }

}
