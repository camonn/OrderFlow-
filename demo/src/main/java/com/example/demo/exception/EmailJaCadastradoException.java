package com.example.demo.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("E-mail já cadastrado: " + email);
    }
}
