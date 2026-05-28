package com.example.demo.exception;

public class ItemPedidoNotFoundException extends RuntimeException {

    public ItemPedidoNotFoundException(Long id) {
        super("Item de pedido com ID " + id + " não encontrado");
    }
}
