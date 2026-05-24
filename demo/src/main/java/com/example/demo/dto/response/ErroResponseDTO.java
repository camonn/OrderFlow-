package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDTO(
        int status,
        String mensagem,
        List<String> erros,
        LocalDateTime timestamp
) {
    public ErroResponseDTO(int status, String mensagem) {
        this(status, mensagem, null, LocalDateTime.now());
    }

    public ErroResponseDTO(int status, String mensagem, List<String> erros) {
        this(status, mensagem, erros, LocalDateTime.now());
    }
}
