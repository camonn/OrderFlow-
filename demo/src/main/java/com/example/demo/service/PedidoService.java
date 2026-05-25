package com.example.demo.service;

import com.example.demo.dto.request.ItemPedidoRequestDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.ItemPedido;
import com.example.demo.entity.Pedido;
import com.example.demo.entity.Produto;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ItemPedidoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido criarPedido(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .data(LocalDateTime.now())
                .status("PENDENTE")
                .totalValue(0.0)
                .build();

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido adicionarItem(Long pedidoId, ItemPedidoRequestDTO dto) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Double subtotal = produto.getPrice() * dto.getAmount();

        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .produto(produto)
                .amount(dto.getAmount())
                .subtotal(subtotal)
                .build();

        itemPedidoRepository.save(itemPedido);

        pedido.setTotalValue(pedido.getTotalValue() + subtotal);

        return pedidoRepository.save(pedido);
    }
}