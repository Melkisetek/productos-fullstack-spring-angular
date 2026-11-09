package com.srmelqui.api.config;

import com.srmelqui.api.service.AuthService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // habilitar la configuración personalizada de seguridad web Spring Boot 4
public class SecurityConfig {

    private final AuthService userService; // Inyeción de nuestro cargador de datos de usuario
    private final  JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(AuthService userService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.jwtAuthFilter = jwtAuthenticationFilter;
    }

    /**
     * Define el algoritmo de hashing para contraseñas Convierte contraseñas
     * legibles en hashes irreversibles de BCrypt.
     *
     * @return
     */
    @Bean
    public PasswordEncoder codificarContrasenia() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Motor principal de Spring Security encargado de coordinar la
     * autenticación. Lo exponemos como un Bean para poder inyectarlo y usarlo
     * dentro del AuthController.
     *
     * @param authConfig
     * @return
     * @throws java.lang.Exception
     */
    @Bean
    public AuthenticationManager autenticacion(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Define las reglas de CORS específicas para que Angular se comunique sin
     * bloqueos.
     * @return 
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracion = new CorsConfiguration();

        // 1. Permitimos explícitamente el origen de tu servidor local de Angular
        configuracion.setAllowedOrigins(List.of("http://localhost:4200"));

        // 2. Permitimos los métodos HTTP estándares que utiliza tu CRUD
        configuracion.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 3. Permitimos cabeceras esenciales (Content-Type para JSON y Authorization para el futuro JWT)
        configuracion.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // 4. Permitimos el envío de credenciales/cookies si fuera necesario
        configuracion.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", configuracion); // Aplica a todos los endpoints del backend
        return fuente;
    }

    /**
     * Configuración del pipeline de seguridad (Filtros HTTP). Determina quién
     * pasa, quién es bloqueado y qué reglas aplican al servidor.
     *
     * @param http
     * @param authmanger
     * @return
     * @throws java.lang.Exception
     */
    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http,
            AuthenticationManager authmanger) throws Exception {

        http
                // IMPORTANTE: Registramos la configuración de CORS antes de evaluar las rutas
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Desactivar la proteccón CSRF debido a que las peticiones se autenticarán vía cabeceras JWT y no por Cookies
                .csrf(csrf -> csrf.disable())
                // Reglas de autorización para rutas HTTP
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // La ruta del login DEBE ser pública
                 .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER") // Restricción del CRUD por roles
                .anyRequest().authenticated() // Cualquier otra URL requiere sesión iniciada
                )
                // Establecer la política de sesiones como STATELESS (Sin estado)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Asigna el manejador y el servicio de carga de usuarios del ecosistema
                .authenticationManager(authmanger)
                .userDetailsService(userService)
                
                // IMPORTANTE: Aregamos nuestro filtro JWT justo antes del filtro de autenticación estándar de Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                // Mantienes activos mecanismos por defecto útiles para pruebas básicas de endpoints
//                .formLogin(fomr -> fomr.permitAll())
//                .httpBasic(basic -> {
//                });

        return http.build();
    }
}
