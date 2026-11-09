package com.srmelqui.api.repository;

import com.srmelqui.api.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario>findByUsuario(String usuario);
}
