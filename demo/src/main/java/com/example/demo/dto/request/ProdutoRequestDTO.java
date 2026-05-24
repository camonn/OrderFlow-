package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        Double price

) {
}
