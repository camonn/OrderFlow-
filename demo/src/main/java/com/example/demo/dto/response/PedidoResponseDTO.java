package com.example.demo.dto.response;

import com.example.demo.entity.PedidoStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime data,
        PedidoStatus status,
        Double totalValue,
        Long clienteId,
        String clienteNome,
        List<ItemPedidoResponseDTO> itens
) {
}
