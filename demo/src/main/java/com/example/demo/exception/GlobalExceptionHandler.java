package com.example.demo.exception;

import com.example.demo.dto.response.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleClienteNotFound(ClienteNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroResponseDTO> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErroResponseDTO(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(HttpStatus.BAD_REQUEST.value(), "Dados inválidos", erros));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno no servidor"));
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleProdutoNotFound(ProdutoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handlePedidoNotFound(PedidoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ItemPedidoNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleItemPedidoNotFound(ItemPedidoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(AlteracaoPedidoNaoPermitidaException.class)
    public ResponseEntity<ErroResponseDTO> handleAlteracaoPedidoNaoPermitida(AlteracaoPedidoNaoPermitidaException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroResponseDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage()));
    }
}
