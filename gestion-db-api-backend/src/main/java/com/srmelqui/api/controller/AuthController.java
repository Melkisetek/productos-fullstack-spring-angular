package com.srmelqui.api.controller;

import com.srmelqui.api.dto.AuthResponse;
import com.srmelqui.api.dto.LoginRequest;
import com.srmelqui.api.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth") // Coincide con la ruta permitida en SecurityConfig
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Inyección por constructor (Buena práctica recomendada)
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint expuesto para recibir credenciales de Angular. Valida el
     * usuario, delega a Spring Security la verificación y retorna el estado de
     * autenticación.
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. Envuelve las credenciales en un token interno para que Spring Security las procese
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsuario(), loginRequest.getPassword());
        // 2. El AuthenticationManager valida al usuario (internamente llamará a AuthService y cargará las credenciales)
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 3. Extrae la información del usuario autenticado satisfactoriamente
        String nombreUsuario = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        // AQUÍ: Generamos un token JWT real firmado digitalmente con JWT
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String tokenJwtReal = jwtService.generarToken(userDetails);

        // 5. Construye la respuesta estructurada y responde un estado HTTP 200 (OK)
        AuthResponse respuesta = new AuthResponse(tokenJwtReal, nombreUsuario, roles);
        return ResponseEntity.ok(respuesta);
    }
}
