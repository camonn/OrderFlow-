package com.example.demo.controller;

import com.example.demo.dto.request.ItemPedidoRequestDTO;
import com.example.demo.entity.Pedido;
import com.example.demo.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public Pedido criarPedido(@RequestParam Long clienteId) {
        return pedidoService.criarPedido(clienteId);
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @PostMapping("/{pedidoId}/itens")
    public Pedido adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedidoRequestDTO dto
    ) {
        return pedidoService.adicionarItem(pedidoId, dto);
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public Pedido removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId
    ) {
        return pedidoService.removerItem(pedidoId, itemId);
    }
}