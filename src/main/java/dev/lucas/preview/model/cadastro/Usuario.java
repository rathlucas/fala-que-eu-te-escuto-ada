package dev.lucas.preview.model;

import dev.lucas.preview.model.Cliente;
import dev.lucas.preview.model.Empresa;

public sealed abstract class Usuario permits Empresa, Cliente {

}
