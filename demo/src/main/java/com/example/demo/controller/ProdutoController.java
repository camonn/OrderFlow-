package com.example.demo.controller;

import com.example.demo.dto.request.ProdutoRequestDTO;
import com.example.demo.dto.response.ProdutoResponseDTO;
import com.example.demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @Operation(summary = "Listar produtos")
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(nome, pageable));
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Remover produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}