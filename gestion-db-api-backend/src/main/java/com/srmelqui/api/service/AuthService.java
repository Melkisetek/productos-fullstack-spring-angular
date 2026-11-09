package com.srmelqui.api.service;

import com.srmelqui.api.entity.Usuario;
import com.srmelqui.api.repository.UsuariosRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    /**
     * Método central de Spring Security. Intercepta la petición, busca al
     * usuario en nuestra base de datos MariaDB y lo traduce a un formato
     * legible para el framework ('UserDetails').
     *
     * @param nombreUsuario
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        // 1. Busca en la tabla 'usuarios' a través de JPA
        Usuario usuario = usuariosRepository.findByUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + nombreUsuario));

        // 2. Transforma la entidad local en un objeto 'User' interno de Spring.
        // Importante: Concatena "ROLE_" al rol string del modelo (ej: ADMIN -> ROLE_ADMIN)
        // para que haga match perfecto con las restricciones declaradas en el SecurityConfig.
        return new User(usuario.getUsuario(), usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())));
    }

}
