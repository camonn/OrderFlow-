package com.example.demo.dto;

public record ProdutoResponseDTO(

        Long id,
        String name,
        String description,
        Double price

) {
}