package com.example.demo.exception;

public class ClienteComPedidosException extends RuntimeException {

    public ClienteComPedidosException(Long clienteId) {
        super("Cliente com ID " + clienteId + " possui pedidos vinculados e não pode ser excluído");
    }
}
