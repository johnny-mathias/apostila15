package br.com.fiap.ecommerce.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AtualizarCategoriaDto {

    @NotBlank(message = "Código é obrigatório!")
    @Size(max = 6)
    @PositiveOrZero
    private int codigo;

    @NotBlank(message = "Nome é obrigatório!")
    @Size(max = 20)
    private String nome;

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

}
