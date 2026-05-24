package com.example.demo.service;


import com.example.demo.dto.request.ProdutoRequestDTO;
import com.example.demo.dto.response.ProdutoResponseDTO;
import com.example.demo.entity.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {

        Produto produto = Produto.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .build();

        Produto salvo = repository.save(produto);

        return toDTO(salvo);
    }

    public List<ProdutoResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setName(dto.name());
        produto.setDescription(dto.description());
        produto.setPrice(dto.price());

        Produto atualizado = repository.save(produto);

        return toDTO(atualizado);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private ProdutoResponseDTO toDTO(Produto produto) {

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getName(),
                produto.getDescription(),
                produto.getPrice()
        );
    }
}