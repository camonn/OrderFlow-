package com.example.demo.dto.response;

public record ItemPedidoResponseDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        Integer amount,
        Double subtotal
) {
}
