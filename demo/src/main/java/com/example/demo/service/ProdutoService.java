package com.example.demo.service;

import com.example.demo.dto.request.ProdutoRequestDTO;
import com.example.demo.dto.response.ProdutoResponseDTO;
import com.example.demo.entity.Produto;
import com.example.demo.exception.ProdutoEmPedidoException;
import com.example.demo.exception.ProdutoNotFoundException;
import com.example.demo.repository.ItemPedidoRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ProdutoService(ProdutoRepository repository, ItemPedidoRepository itemPedidoRepository) {
        this.repository = repository;
        this.itemPedidoRepository = itemPedidoRepository;
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

    public Page<ProdutoResponseDTO> listar(String nome, Pageable pageable) {

        Page<Produto> produtos;

        if (nome != null && !nome.isBlank()) {
            produtos = repository.findByNameContainingIgnoreCase(nome, pageable);
        } else {
            produtos = repository.findAll(pageable);
        }

        return produtos.map(this::toDTO);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        return toDTO(produto);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        produto.setName(dto.name());
        produto.setDescription(dto.description());
        produto.setPrice(dto.price());

        Produto atualizado = repository.save(produto);

        return toDTO(atualizado);
    }

    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new ProdutoNotFoundException(id);
        }

        if (itemPedidoRepository.existsByProdutoId(id)) {
            throw new ProdutoEmPedidoException(id);
        }

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