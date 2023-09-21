package dev.lucas.preview.model.cadastro;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public sealed abstract class Usuario permits Empresa, Cliente {

}
