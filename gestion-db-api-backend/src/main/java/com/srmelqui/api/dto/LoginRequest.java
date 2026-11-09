package com.srmelqui.api.dto;
/**
 * DTO que mapea el cuerpo JSON enviado desde Angular
 * durante la solicitud de inicio de sesión.
 */
public class LoginRequest {
    private String usuario;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

}
