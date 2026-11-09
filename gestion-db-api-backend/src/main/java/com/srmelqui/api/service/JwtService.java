package com.srmelqui.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY_STRING = "EstaEsUnaClaveSecretaMuyLargaYSeguraParaMiiSistemaDeInventario123!";
    private static final long EXPIRATION_TIME = 1000 * 60 * 30;

    /**
     * Genera un obejto SecretKey seguro a partir de nuestro String de
     * configuración.
     *
     * @return
     */
    private SecretKey obtenerFirmaKey() {
        byte[] keyBytes = SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Crea un token JWT real cifrado para un usuario autenticado.
     *
     * @param userDetails
     * @return
     */
    public String generarToken(UserDetails userDetails) {
        Map<String, Object> extracClaims = new HashMap<>();

        // Almacenar los roles del usuario dentro del token ("Claims")
        extracClaims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
                .claims(extracClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(obtenerFirmaKey())
                .compact();

    }

    /**
     * Extrae el nombre de usuario (Subject) contenido en el token.
     *
     * @param token
     * @return
     */
    public String extraerUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token pertenece al usuario y si no ha expirado.
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean esTokenValido(String token, UserDetails userDetails) {
        final String usuario = extraerUsuario(token);
        return (usuario.equals(userDetails.getUsername()) && !estaTokenExpirado(token));
    }

    private boolean estaTokenExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Método genérico helper para extraer cualquier información interna (Claim)
     * del token.
     *
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(obtenerFirmaKey()) // Valida que la firma no haya sido alterado
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
