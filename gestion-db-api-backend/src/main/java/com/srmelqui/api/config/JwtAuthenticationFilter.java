package com.srmelqui.api.config;

import com.srmelqui.api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1. Extraemos la cabecera 'Authorization' de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String nombreUsuario;

        // 2. Si la cabecera no existe o no empieza con "Bearer ", ignoramos y dejamos continuar al siguente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraemos el texto puro del token (quitando la palabra "Bearer " que mide 7 caracteres)
        jwt = authHeader.substring(7);
        nombreUsuario = jwtService.extraerUsuario(jwt);

        // 4. Si hay un usuario válido y la petición no ha sido autenticada en el contexto actual de Spring
        if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscamos al usuario en MariaDB usando nuestro servicio
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(nombreUsuario);

            // 5. Si el token es criptográficamente correcto, creamos la credencial interna de Spring
            if (jwtService.esTokenValido(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // Aquí se inyectan los Roles ("ROLE_ADMIN", etc.)
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. ¡PASO CLAVE!: Seteamos al usuario en el contexto global de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Dejamos continuar la petición hacia el controlador correspondiente
        filterChain.doFilter(request, response);
    }
    
}
