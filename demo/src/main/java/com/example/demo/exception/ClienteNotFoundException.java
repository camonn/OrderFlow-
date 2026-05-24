package com.example.demo.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("Cliente não encontrado com id: " + id);
    }
}
