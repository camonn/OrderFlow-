package com.example.demo.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(Long id) {
        super("Pedido com ID " + id + " não encontrado");
    }
}
