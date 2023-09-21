package dev.lucas.preview.repository;

import dev.lucas.preview.model.cadastro.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findById(UUID id);
}
