package dev.lucas.preview.repository;

import dev.lucas.preview.model.postagem.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostagemRepository extends JpaRepository<Postagem, Integer> {
}
