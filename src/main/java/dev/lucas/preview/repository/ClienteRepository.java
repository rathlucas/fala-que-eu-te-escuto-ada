package dev.lucas.preview.repository;

import dev.lucas.preview.model.cadastro.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
