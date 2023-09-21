package dev.lucas.preview.repository;

import dev.lucas.preview.model.postagem.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostagemRepository extends JpaRepository<Postagem, UUID> {
}
