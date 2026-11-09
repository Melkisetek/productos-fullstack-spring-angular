package com.srmelqui.api.dto;

import java.util.List;

/**
 * DTO que encapsula la respuesta exitosa del backend. Devuelve el token JWT
 * generado junto con información básica del usuario.
 */
public class AuthResponse {

    private String token;
    private String usuario;
    private List<String> roles;

    public AuthResponse() {
    }

    public AuthResponse(String token, String usuario, List<String> roles) {
        this.token = token;
        this.usuario = usuario;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
