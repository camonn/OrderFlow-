package com.example.demo.controller;

import com.example.demo.dto.request.ItemPedidoRequestDTO;
import com.example.demo.entity.Pedido;
import com.example.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos e itens")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Criar pedido para um cliente")
    @PostMapping
    public Pedido criarPedido(@RequestParam Long clienteId) {
        return pedidoService.criarPedido(clienteId);
    }

    @Operation(summary = "Listar todos os pedidos")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @Operation(summary = "Adicionar item ao pedido")
    @PostMapping("/{pedidoId}/itens")
    public Pedido adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedidoRequestDTO dto
    ) {
        return pedidoService.adicionarItem(pedidoId, dto);
    }

    @Operation(summary = "Remover item do pedido")
    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public Pedido removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId
    ) {
        return pedidoService.removerItem(pedidoId, itemId);
    }
}