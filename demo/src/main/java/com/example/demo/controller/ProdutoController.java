package com.example.demo.controller;

import com.example.demo.dto.request.ProdutoRequestDTO;
import com.example.demo.dto.response.ProdutoResponseDTO;
import com.example.demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar produto")
    @PostMapping
    public ProdutoResponseDTO criar(
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.criar(dto);
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return service.listar();
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @Operation(summary = "Remover produto")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}