package dev.lucas.preview.repository;

import dev.lucas.preview.model.postagem.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurtidaRepository extends JpaRepository<Curtida, UUID> {
}
