package com.example.demo.exception;

public class AlteracaoPedidoNaoPermitidaException extends RuntimeException {

    public AlteracaoPedidoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}
