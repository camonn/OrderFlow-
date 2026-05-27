package com.example.demo.exception;

public class ProdutoEmPedidoException extends RuntimeException {

    public ProdutoEmPedidoException(Long produtoId) {
        super("Produto com ID " + produtoId + " está vinculado a pedidos e não pode ser excluído");
    }
}
