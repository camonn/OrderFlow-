package com.example.demo.dto.request;

import com.example.demo.enums.PedidoStatus;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusPedidoRequestDTO(

        @NotNull(message = "Status é obrigatório")
        PedidoStatus status

) {
}
